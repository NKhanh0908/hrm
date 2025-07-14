package com.project.hrm.document.service.serviceimpl;

import com.project.hrm.auth.dto.AccountDTO;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.auth.enums.AccountRole;
import com.project.hrm.auth.service.AccountService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocumentApproverServiceImpl implements DocumentApproverService {
    private final DocumentApproverMapper documentApproverMapper;
    private final DocumentApproverRepository documentApproverRepository;
    private final EmployeeService employeeService;
    private final DocumentsService documentsService;
    private final EmployeeRepository employeeRepository;
    private final AccountService accountService;

    public DocumentApproverServiceImpl(DocumentApproverMapper documentApproverMapper,
                                   DocumentApproverRepository documentApproverRepository,
                                   EmployeeService employeeService,
                                   @Lazy DocumentsService documentsService,
                                   EmployeeRepository employeeRepository,
                                       AccountService accountService) {
        this.documentApproverMapper = documentApproverMapper;
        this.documentApproverRepository = documentApproverRepository;
        this.employeeService = employeeService;
        this.documentsService = documentsService;
        this.employeeRepository = employeeRepository;
        this.accountService = accountService;
    }

    @Transactional
    @Override
    public List<DocumentApproverDTO> createApproversForDocument(Documents document) {
        log.info("Creating approvers for document: {}", document.getId());

        List<Employees> approvers = switch (document.getDocumentScope()) {
            case PERSONAL, RESTRICTED -> List.of();

            case DEPARTMENT -> {
                log.info("Creating department approvers for document: {}", document.getId());
                Departments uploaderDept = document.getUploadedBy().getRole().getDepartments();
                log.info("Uploader department: {}", document.getUploadedBy().getId());
                log.info("Uploader department ID: {}", uploaderDept.getId());

                AccountDTO accountDTO = accountService.getAccountByEmployeeId(document.getUploadedBy().getId());
                log.info("Uploader account role: {}", accountDTO.getRole());
                AccountRole uploaderRole = AccountRole.valueOf(accountDTO.getRole());
                log.info("Uploader role: {}", uploaderRole);
                List<AccountRole> roles = List.of(AccountRole.MANAGER, AccountRole.SUPERVISOR);
                List<Employees> deptApprovers = employeeRepository
                        .findApproversByDepartmentAndRoles(uploaderDept.getId(), roles.stream().map(Enum::name).toList());
                log.info("Found {} approvers in department: {}", deptApprovers.size(), uploaderDept.getId());

                yield deptApprovers.stream()
                        .filter(emp -> {
                            log.info("Emp ID: {}, Role name: {}", emp.getId(), emp.getRole().getName());
                            AccountDTO employee = accountService.getAccountByEmployeeId(emp.getId());
                            log.info("Employee account role: {}", employee.toString());
                            AccountRole approverRole = AccountRole.valueOf(employee.getRole());
                            log.info("Approver role: {}", approverRole);
                            if (emp.getId().equals(document.getUploadedBy().getId())) {
                                return false;
                            }
                            return roleIsHigher(approverRole, uploaderRole);
                        })
                        .toList();
            }

            case COMPANY -> {
                log.info("Creating company approvers for document: {}", document.getId());
                List<AccountRole> roles = List.of(AccountRole.HR, AccountRole.ADMIN);
                yield employeeRepository
                        .findByRoleAndAllowedStatus_Native(roles.stream().map(Enum::name).toList())
                        .stream()
                        .filter(emp -> !emp.getId().equals(document.getUploadedBy().getId()))
                        .toList();
            }
        };
        log.info("Creating approvers for document: {}", approvers.toString());

        List<DocumentApprover> createDTO = approvers.stream()
                .map(approver -> DocumentApprover.builder()
                        .id(IdGenerator.getGenerationId())
                        .document(document)
                        .approver(approver)
                        .canApprove(true)
                        .build())
                .toList();

        List<DocumentApprover> saved = documentApproverRepository.saveAll(createDTO);
        log.info("[DocumentApproverServiceImpl] - Đã tạo {} bản ghi DocumentApprover cho document ID: {}", saved.size(), document.getId());

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
