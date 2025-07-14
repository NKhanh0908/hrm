package com.project.hrm.document.service.serviceimpl;

import com.project.hrm.auth.enums.AccountRole;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.department.entity.Departments;
import com.project.hrm.department.service.DepartmentService;
import com.project.hrm.document.dto.documentApproverDTO.DocumentApproverCreateDTO;
import com.project.hrm.document.dto.documentApproverDTO.DocumentApproverDTO;
import com.project.hrm.document.entity.DocumentApprovals;
import com.project.hrm.document.entity.DocumentApprover;
import com.project.hrm.document.entity.Documents;
import com.project.hrm.document.mapper.DocumentApprovalsMapper;
import com.project.hrm.document.mapper.DocumentApproverMapper;
import com.project.hrm.document.repository.DocumentApproverRepository;
import com.project.hrm.document.service.DocumentApproverService;
import com.project.hrm.document.service.DocumentsService;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.employee.repository.EmployeeRepository;
import com.project.hrm.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentApproverServiceImpl implements DocumentApproverService {
    private final DocumentApproverMapper documentApproverMapper;
    private final DocumentApproverRepository documentApproverRepository;
    private final EmployeeService employeeService;
    private final DocumentsService documentsService;
    private final EmployeeRepository employeeRepository;

    public DocumentApproverServiceImpl(DocumentApproverMapper documentApproverMapper,
                                   DocumentApproverRepository documentApproverRepository,
                                   EmployeeService employeeService,
                                   @Lazy DocumentsService documentsService,
                                   EmployeeRepository employeeRepository) {
        this.documentApproverMapper = documentApproverMapper;
        this.documentApproverRepository = documentApproverRepository;
        this.employeeService = employeeService;
        this.documentsService = documentsService;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public List<DocumentApproverDTO> createApproversForDocument(Documents document) {
        List<Employees> approvers = switch (document.getDocumentScope()) {
            case PERSONAL, RESTRICTED -> List.of();

            case DEPARTMENT -> {
                Departments uploaderDept = document.getUploadedBy().getRole().getDepartments();
                AccountRole uploaderRole = AccountRole.valueOf(document.getUploadedBy().getRole().getName());

                List<AccountRole> roles = List.of(AccountRole.MANAGER, AccountRole.SUPERVISOR);
                List<Employees> deptApprovers = employeeRepository
                        .findApproversByDepartmentAndRoles(uploaderDept.getId(), roles.stream().map(Enum::name).toList());

                yield deptApprovers.stream()
                        .filter(emp -> {
                            AccountRole approverRole = AccountRole.valueOf(emp.getRole().getName());
                            if (emp.getId().equals(document.getUploadedBy().getId())) {
                                return false;
                            }
                            return roleIsHigher(approverRole, uploaderRole);
                        })
                        .toList();
            }

            case COMPANY -> {
                List<AccountRole> roles = List.of(AccountRole.HR, AccountRole.ADMIN);
                yield employeeRepository
                        .findByRoleAndAllowedStatus_Native(roles.stream().map(Enum::name).toList())
                        .stream()
                        .filter(emp -> !emp.getId().equals(document.getUploadedBy().getId()))
                        .toList();
            }
        };

        if (approvers.isEmpty()) {
            return null;
        }

        List<DocumentApprover> createDTO = approvers.stream()
                .map(approver -> DocumentApprover.builder()
                        .id(IdGenerator.getGenerationId())
                        .document(document)
                        .approver(approver)
                        .canApprove(true)
                        .build())
                .toList();

        List<DocumentApprover> saved = documentApproverRepository.saveAll(createDTO);

        return documentApproverMapper.convertPageToListDTO(saved);
    }

    @Transactional
    @Override
    public DocumentApproverDTO create(DocumentApproverCreateDTO documentApproverCreateDTO) {
        Employees approver = employeeService.getEntityById(documentApproverCreateDTO.getApproverId());
        Documents documents = documentsService.getEntityById(documentApproverCreateDTO.getDocumentId());
        DocumentApprover documentApprover = DocumentApprover.builder()
                .id(IdGenerator.getGenerationId())
                .document(documents)
                .approver(approver)
                .canApprove(documentApproverCreateDTO.isCanApprove())
                .build();
        return documentApproverMapper.covertEntityToDTO(documentApproverRepository.save(documentApprover));
    }

    @Transactional
    @Override
    public DocumentApproverDTO update(DocumentApproverDTO documentApproverDTO) {
        return null;
    }

    @Transactional
    @Override
    public DocumentApproverDTO getDTOById(Integer id) {
        return documentApproverMapper.covertEntityToDTO(getEntityById(id));
    }

    @Transactional
    @Override
    public DocumentApprover getEntityById(Integer id) {
        return documentApproverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document Approver not found with id: " + id));
    }

    @Transactional
    @Override
    public void deleteApproversByDocumentId(Integer documentId) {

    }

    private boolean roleIsHigher(AccountRole approverRole, AccountRole uploaderRole) {
        // Nếu ordinal của approver nhỏ hơn ordinal của uploader thì approver cao hơn
        return approverRole.ordinal() < uploaderRole.ordinal();
    }
}
