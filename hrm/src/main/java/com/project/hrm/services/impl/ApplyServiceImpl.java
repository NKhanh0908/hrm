package com.project.hrm.services.impl;

import com.project.hrm.dto.applyDTO.*;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.othersDTO.InfoApply;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import com.project.hrm.entities.Apply;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.enums.ApplyStatus;
import com.project.hrm.enums.RecruitmentStatus;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.ApplyMapper;
import com.project.hrm.mapper.CandidateProfileMapper;
import com.project.hrm.repositories.ApplyRepository;
import com.project.hrm.services.*;
import com.project.hrm.specifications.ApplySpecification;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ApplyRepository applyRepository;

    private final ApplyMapper applyMapper;
    private final CandidateProfileMapper candidateProfileMapper;

    private final RecruitmentService recruitmentService;
    private final CandidateProfileService candidateProfileService;
    private final EmployeeService employeeService;
    private final ContractService contractService;
    private final MailService mailService;

    /**
     * Creates a new Apply entity using the provided creation DTO.
     *
     * @param applyCreateDTO the DTO containing data to create a new Apply record.
     *                       Includes references to Recruitment and CandidateProfile by ID.
     * @return the created Apply entity as a DTO after being persisted.
     * @throws RuntimeException if referenced Recruitment or CandidateProfile is not found.
     */
    @Transactional
    @Override
    public ApplyDTO create(ApplyCreateDTO applyCreateDTO) {
        log.info("Create Apply");

        CandidateProfileDTO candidateProfileDTO = candidateProfileService.create(applyCreateDTO.getCandidateProfileCreateDTO());

        Recruitment recruitment = recruitmentService.getEntityById(applyCreateDTO.getRecruitmentId());

        if (recruitment.getStatus() != RecruitmentStatus.OPEN) {
            throw new CustomException(Error.APPLY_NOT_OPEN);
        }

        Apply apply = applyMapper.convertCreateDTOToEntity(applyCreateDTO, recruitment,
                candidateProfileMapper.toEntity(candidateProfileDTO));
        apply.setId(IdGenerator.getGenerationId());

        return applyMapper.toDTO(applyRepository.save(apply));
    }

    /**
     * Updates an existing Apply entity based on non-null fields in the provided DTO.
     *
     * @param applyUpdateDTO the DTO containing fields to update, including the ID of the Apply.
     * @return the updated Apply entity as a DTO after changes are persisted.
     * @throws RuntimeException if the Apply, Recruitment, or CandidateProfile with the provided ID does not exist.
     */
    @Override
    @Transactional
    public ApplyDTO update(ApplyUpdateDTO applyUpdateDTO) {
        log.info("Update Apply");

        Apply apply = getEntityById(applyUpdateDTO.getId());

        if (applyUpdateDTO.getApplyAt() != null) {
            apply.setApplyAt(applyUpdateDTO.getApplyAt());
        }

        if (applyUpdateDTO.getStatus() != null) {
            apply.setApplyStatus(ApplyStatus.valueOf(applyUpdateDTO.getStatus()));
        }

        if (applyUpdateDTO.getPosition() != null) {
            apply.setPosition(applyUpdateDTO.getPosition());
        }

        if (applyUpdateDTO.getRecruitmentId() != null) {
            Recruitment recruitment = recruitmentService.getEntityById(applyUpdateDTO.getRecruitmentId());
            apply.setRecruitment(recruitment);
        }

        if (applyUpdateDTO.getCandidateProfileId() != null) {
            CandidateProfile candidateProfile = candidateProfileService.getEntityById(applyUpdateDTO.getCandidateProfileId());
            apply.setCandidateProfile(candidateProfile);
        }

        Apply updated = applyRepository.save(apply);
        return applyMapper.toDTO(updated);
    }

    /**
     * Marks the given application as invited to interview, sends an interview notification,
     * and returns the updated application DTO.
     *
     * <p>Process:
     * <ol>
     *   <li>Retrieve the {@link Apply} entity by the ID in {@link InterviewLetter}.</li>
     *   <li>Update its status to {@link ApplyStatus#INTERVIEW} via {@link #updateStatus(Integer, ApplyStatus)}</li>
     *   <li>Fetch applicant contact info via {@link InfoApply} projection.</li>
     *   <li>Send an email notification with interview details using {@link MailService#notificationInterview(InfoApply, InterviewLetter)}</li>
     * </ol>
     *
     * @param interviewLetter the details of the interview, including applyId, address, and interviewTime
     * @return the updated {@link ApplyDTO} reflecting the new INTERVIEW status
     * @throws RuntimeException if the applyId is not found or notification fails
     */
    @Transactional
    @Override
    public ApplyDTO interview(InterviewLetter interviewLetter) {
        log.info("Interview");

        Apply apply = getEntityById(interviewLetter.getApplyId());

        ApplyDTO applyDTO = updateStatus(apply.getId(), ApplyStatus.INTERVIEW);

        InfoApply infoApply = applyRepository.getInfoApply(apply.getId());

        mailService.notificationInterview(infoApply, interviewLetter);

        return applyDTO;
    }

    /**
     * Rejects an application and notifies the candidate via email.
     *
     * @param applyId the ID of the application to be rejected
     * @return the updated {@link ApplyDTO} with status set to REJECTED
     */
    @Transactional
    @Override
    public ApplyDTO rejectApply(Integer applyId) {
        ApplyDTO applyDTO = updateStatus(applyId, ApplyStatus.REJECTED);

        InfoApply infoApply = applyRepository.getInfoApply(applyId);

        mailService.notificationForRejection(infoApply);

        return applyDTO;
    }

    /**
     * Marks an application as hired, creates an employee profile,
     * and generates a virtual contract for the candidate.
     *
     * @param applyId the ID of the hired application
     * @param details
     * @return the updated {@link ApplyDTO} with status set to HIRED
     */
    @Transactional
    @Override
    public ApplyDTO hiredApply(Integer applyId, JobOfferDetailsDTO details) {
        ApplyDTO applyDTO = updateStatus(applyId, ApplyStatus.HIRED);

        CandidateProfile candidateProfile = candidateProfileService.getEntityByApplyId(applyId);

        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO();
        employeeCreateDTO.setFirstName(candidateProfile.getName());
        employeeCreateDTO.setEmail(candidateProfile.getEmail());
        employeeCreateDTO.setPhone(candidateProfile.getPhone());

        EmployeeDTO employeeDTO = employeeService.create(employeeCreateDTO);

        ContractCreateDTO contractCreateDTO = new ContractCreateDTO();
        contractCreateDTO.setTitle("Virtual Contract");
        contractCreateDTO.setDescription("Temporary contract after candidate has passed the interview round");
        contractCreateDTO.setEmployeeId(employeeDTO.getId());
        contractCreateDTO.setRoleId(getRoleIdByApplyId(applyId));
        contractService.create(contractCreateDTO);

        InfoApply infoApply = applyRepository.getInfoApply(applyId);
        mailService.notificationForHired(infoApply, details);

        return applyDTO;
    }

    /**
     * Change the status on an existing Apply.
     *
     * @param id     the Apply ID
     * @param status the new {@link ApplyStatus}
     * @return the updated {@link ApplyDTO}
     * @throws EntityNotFoundException if no Apply with given ID exists
     */
    @Transactional
    @Override
    public ApplyDTO updateStatus(Integer id, ApplyStatus status) {
        log.info("Update status apply");

        if (!applyRepository.existsById(id)) {
            throw new CustomException(Error.APPLY_NOT_FOUND);
        }

        int updated = applyRepository.updateStatus(id, status.name());

        if (updated != 1) {
            throw new CustomException(Error.APPLY_UNABLE_TO_UPDATE);
        }

        return applyMapper.toDTO(getEntityById(id));
    }

    /**
     * Retrieves an ApplyDTO by its unique ID.
     *
     * @param id the ID of the Apply entity to retrieve.
     * @return the corresponding ApplyDTO.
     * @throws RuntimeException if no Apply entity is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public ApplyDTO getById(Integer id) {
        log.info("Find Apply by id: {}", id);

        return applyMapper.toDTO(getEntityById(id)
        );
    }

    /**
     * Retrieves the Apply entity by its ID.
     *
     * @param id the ID of the Apply entity to retrieve.
     * @return the corresponding to Apply entity.
     * @throws RuntimeException if no Apply entity is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public Apply getEntityById(Integer id) {
        log.info("Find Apply entity by id: {}", id);

        return applyRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.APPLY_NOT_FOUND));
    }

    @Override
    public Integer getRoleIdByApplyId(Integer applyId) {
        return applyRepository.getRoleIdByApplyId(applyId);
    }

    /**
     * Filters and retrieves a paginated list of ApplyDTOs based on the provided filter criteria.
     *
     * @param applyFilter the filter criteria including status, position, recruitment ID, etc.
     * @param page the page number to retrieve (0-based).
     * @param size the number of records per page.
     * @return a list of matching ApplyDTOs for the specified page.
     */
    @Transactional(readOnly = true)
    @Override
    public List<ApplyDTO> filter(ApplyFilter applyFilter, int page, int size) {
        log.info("Filter Apply");

        Specification<Apply> applySpecification = ApplySpecification.filter(applyFilter);

        Pageable pageable = PageRequest.of(page, size, Sort.by("applyAt").descending());

        Page<Apply> applyPage = applyRepository.findAll(applySpecification, pageable);

        return applyMapper.convertPageToList(applyPage);
    }

}
