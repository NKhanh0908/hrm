package com.project.hrm.payroll.services.impl;

import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardDTO;
import com.project.hrm.payroll.entities.Regulations;
import com.project.hrm.payroll.enums.PayrollComponentType;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.payroll.mapper.PayrollComponentMapper;
import com.project.hrm.payroll.dto.payrollComponentsDTO.*;
import com.project.hrm.payroll.entities.PayPeriods;
import com.project.hrm.payroll.entities.PayrollComponents;
import com.project.hrm.payroll.entities.Payrolls;
import com.project.hrm.payroll.repositories.PayrollComponentsRepository;
import com.project.hrm.employee.service.DisciplinaryActionService;
import com.project.hrm.payroll.services.PayrollComponentsService;
import com.project.hrm.payroll.services.RegulationsService;
import com.project.hrm.employee.service.RewardService;
import com.project.hrm.payroll.specifications.PayrollComponentsSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollComponentsServiceImpl implements PayrollComponentsService {
    private final PayrollComponentsRepository payrollComponentsRepository;
    private final PayrollComponentMapper payrollComponentMapper;
    private final RegulationsService regulationsService;
    private final DisciplinaryActionService disciplinaryActionService;
    private final RewardService rewardService;

    /**
     * Creates a new payroll component from the given DTO.
     *
     * @param payrollComponentsCreateDTO the creation data
     * @return the created {@link PayrollComponentsDTO}
     */
    @Transactional
    @Override
    public PayrollComponentsDTO create(PayrollComponentsCreateDTO payrollComponentsCreateDTO) {
        log.info("Create PayrollComponentsDTO");

        PayrollComponents payrollComponents = payrollComponentMapper.toPayrollComponentsFromCreateDTO(payrollComponentsCreateDTO);

        payrollComponents.setRegulation(payrollComponentsCreateDTO.getRegulationId() != null
                ? regulationsService.getEntityById(payrollComponentsCreateDTO.getRegulationId())
                : null);
        return payrollComponentMapper.toPayrollComponentsDTO(payrollComponentsRepository.save(payrollComponents));
    }

    /**
     * Updates an existing payroll component with new data.
     *
     * @param payrollComponentsUpdateDTO the update data
     * @return the updated {@link PayrollComponentsDTO}
     * @throws EntityNotFoundException if the component does not exist
     */
    @Transactional
    @Override
    public PayrollComponentsDTO update(PayrollComponentsUpdateDTO payrollComponentsUpdateDTO) {
        log.info("Update PayrollComponentsDTO");

        PayrollComponents payrollComponents = getEntityById(payrollComponentsUpdateDTO.getId());
        if(payrollComponentsUpdateDTO.getName() != null){
            payrollComponents.setName(payrollComponentsUpdateDTO.getName());
        }
        if (payrollComponentsUpdateDTO.getAmount() != null){
            payrollComponents.setAmount(payrollComponentsUpdateDTO.getAmount());
        }
        if (payrollComponentsUpdateDTO.getPercentage() != null){
            payrollComponents.setPercentage(payrollComponentsUpdateDTO.getPercentage());
        }
        if (payrollComponentsUpdateDTO.getRegulationId() != null){
            payrollComponents.setRegulation(regulationsService.getEntityById(payrollComponentsUpdateDTO.getRegulationId()));
        }
        if (payrollComponentsUpdateDTO.getType() != null){
            payrollComponents.setType(payrollComponentsUpdateDTO.getType());
        }

        PayrollComponents updatedPayrollComponents = payrollComponentsRepository.save(payrollComponents);
        return payrollComponentMapper.toPayrollComponentsDTO(updatedPayrollComponents);
    }

    /**
     * Deletes a payroll component by its ID.
     *
     * @param Id the component ID
     * @throws EntityNotFoundException if not found
     */
    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete PayrollComponentsDTO");
        if (payrollComponentsRepository.existsById(Id)) {
            payrollComponentsRepository.deleteById(Id);
        }else {
            throw new EntityNotFoundException("PayrollComponents not found with id: " + Id);
        }
    }

    /**
     * Checks whether a payroll component exists by ID.
     *
     * @param Id the ID to check
     * @return true if exists, false otherwise
     */
    @Transactional
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence id: {}", Id);
        return payrollComponentsRepository.existsById(Id);
    }

    /**
     * Retrieves a payroll component DTO by ID.
     *
     * @param id the ID of the component
     * @return the {@link PayrollComponentsDTO}
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollComponentsDTO getById(Integer id) {
        log.info("Get PayrollComponentsDTO by id: {}",id);
        return payrollComponentMapper.toPayrollComponentsDTO(getEntityById(id));
    }

    /**
     * Retrieves a payroll component entity by ID.
     *
     * @param id the ID of the component
     * @return the {@link PayrollComponents} entity
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollComponents getEntityById(Integer id) {
        log.info("Get Payroll Components Entity by id: {}", id);
        return payrollComponentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll Components Entity with id: " + id + " not found"));
    }

    /**
     * Filters payroll components based on a dynamic set of conditions.
     *
     * @param payrollComponentFilter the filter criteria
     * @param page                   page number
     * @param size                   records per page
     * @return a list of matching {@link PayrollComponentsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollComponentsDTO> filter(PayrollComponentsFilter payrollComponentFilter, int page, int size) {
        log.info("Filter PayrollComponents");

        Specification<PayrollComponents> payrollComponentsSpecification = PayrollComponentsSpecifications.filter(payrollComponentFilter);

        Pageable pageable = PageRequest.of(page, size);
        return payrollComponentMapper.convertPageEntityToPageDTO(payrollComponentsRepository.findAll(payrollComponentsSpecification, pageable));
    }

    /**
     * Filters payroll components with additional numeric range filtering.
     *
     * @param payrollComponentsFilterWithRange range and type filters
     * @param page                             page number
     * @param size                             records per page
     * @return a list of matching {@link PayrollComponentsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollComponentsDTO> filterWithRange(PayrollComponentsFilterWithRange payrollComponentsFilterWithRange, int page, int size) {
        log.info("Filter with range PayrollComponents");

        Pageable pageable = PageRequest.of(page, size);

        Specification<PayrollComponents> payrollComponentsSpecification = PayrollComponentsSpecifications.filterWithRange(payrollComponentsFilterWithRange);

        return payrollComponentMapper.convertPageEntityToPageDTO(payrollComponentsRepository.findAll(payrollComponentsSpecification, pageable));
    }

    @Transactional
    @Override
    public List<PayrollComponents> createComponents(Payrolls payrolls) {
        log.info("Create PayrollComponents for payroll ID: {}", payrolls.getId());
        Objects.requireNonNull(payrolls, "Payrolls cannot be null");
        Objects.requireNonNull(payrolls.getEmployee(), "Payrolls employee cannot be null");
        Objects.requireNonNull(payrolls.getPayPeriod(), "Payrolls pay period cannot be null");

        List<Regulations> regulationsTax = regulationsService.findRegulationByType(PayrollComponentType.TAX);
        List<Regulations> regulationsInsurance = regulationsService.findRegulationByType(PayrollComponentType.INSURANCE);
        System.out.println(regulationsTax);
        System.out.println(regulationsInsurance);
        List<PayrollComponents> payrollComponentsListReward = createComponentsTypeRewards(payrolls);
        List<PayrollComponents> payrollComponentsListDeduction = createComponentsTypeDeduction(payrolls);
        List<PayrollComponents> payrollComponentsListTax = createComponentsWithRegulations(payrolls, regulationsTax);
        List<PayrollComponents> payrollComponentsListInsurance = createComponentsWithRegulations(payrolls, regulationsInsurance);

        return Stream.of(
                        payrollComponentsListTax,
                        payrollComponentsListInsurance,
                        payrollComponentsListReward,
                        payrollComponentsListDeduction)
                .flatMap(Collection::stream)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public PayrollComponents getPayrollComponentByPayrollIdAndType(Integer payrollId, PayrollComponentType type) {
        log.info("Get Payroll Components by Payroll id and type: {}, {}",payrollId, type);
        return payrollComponentsRepository.findByPayrollIdAndType(payrollId, type)
                .orElseThrow(() -> new EntityNotFoundException("Payroll Components with id: " + payrollId + " and type: " + type + " not found"));
    }

    @Transactional
    @Override
    public Map<Integer, List<PayrollComponents>> createBatchComponents(List<Payrolls> payrollsList) {
        log.info("Creating batch components for {} payrolls", payrollsList.size());
        Map<Integer, List<PayrollComponents>> result = new HashMap<>();

        if (payrollsList.isEmpty()) {
            return result;
        }

        // Preload all data needed for components creation
        List<Integer> employeeIds = payrollsList.stream()
                .map(payroll -> payroll.getEmployee().getId())
                .distinct()
                .toList();

        PayPeriods payPeriod = payrollsList.getFirst().getPayPeriod(); // All payrolls should have same pay period

        // Batch load all data
        Map<Integer, List<DisciplinaryActionDTO>> disciplinaryActionsMap =
                disciplinaryActionService.getBatchDisciplinaryActions(employeeIds, payPeriod.getStartDate(), payPeriod.getEndDate());

        Map<Integer, List<RewardDTO>> rewardsMap =
                rewardService.getBatchRewards(employeeIds, payPeriod.getStartDate(), payPeriod.getEndDate());

        // Cache regulations to avoid multiple queries
        List<Regulations> regulationsTax = regulationsService.findRegulationByType(PayrollComponentType.TAX);
        List<Regulations> regulationsInsurance = regulationsService.findRegulationByType(PayrollComponentType.INSURANCE);

        // Create components for each payroll using preloaded data
        for (Payrolls payroll : payrollsList) {
            try {
                Integer employeeId = payroll.getEmployee().getId();

                // Get preloaded data for this employee
                List<DisciplinaryActionDTO> disciplinaryActions =
                        disciplinaryActionsMap.getOrDefault(employeeId, new ArrayList<>());
                List<RewardDTO> rewards =
                        rewardsMap.getOrDefault(employeeId, new ArrayList<>());

                // Create components using preloaded data
                List<PayrollComponents> components = new ArrayList<>();

                // Add tax and insurance components
                components.addAll(createComponentsWithRegulations(payroll, regulationsTax));
                components.addAll(createComponentsWithRegulations(payroll, regulationsInsurance));

                // Add reward components
                components.addAll(createComponentsTypeRewardsWithPreloadedData(payroll, rewards));

                // Add deduction components
                components.addAll(createComponentsTypeDeductionWithPreloadedData(payroll, disciplinaryActions));

                result.put(payroll.getId(), components);

            } catch (Exception e) {
                log.error("Error creating components for payroll {}: {}", payroll.getId(), e.getMessage(), e);
                result.put(payroll.getId(), new ArrayList<>());
            }
        }

        return result;
    }

    protected List<PayrollComponents> createComponentsWithRegulations(Payrolls payrolls, List<Regulations> regulations) {
        Objects.requireNonNull(payrolls, "Payrolls cannot be null");
        Objects.requireNonNull(regulations, "Regulations list cannot be null");

        return regulations.stream()
                .map(regulation -> {
                    Objects.requireNonNull(regulation.getName(), "Regulation name cannot be null");
                    Objects.requireNonNull(regulation.getType(), "Regulation type cannot be null");
                    if (regulation.getType() != PayrollComponentType.TAX &&
                            regulation.getAmount() == null &&
                            regulation.getPercentage() == null) {
                        throw new CustomException(Error.REGULATION_INVALID_AMOUNT);
                    }

                    PayrollComponents.PayrollComponentsBuilder builder = PayrollComponents.builder()
                            .name(regulation.getName())
                            .type(regulation.getType())
                            .payroll(payrolls)
                            .regulation(regulation);

                    if (regulation.getType() == PayrollComponentType.TAX) {
                        builder.isPercentage(false).percentage(null).amount(null);
                    } else {
                        boolean isPercentage = regulation.getPercentage() != null;
                        builder.amount(regulation.getAmount())
                                .percentage(regulation.getPercentage())
                                .isPercentage(isPercentage);
                        log.info("Create PayrollComponents isPercentage: {}", isPercentage);
                    }

                    return builder.build();
                })
                .toList();
    }

    protected List<PayrollComponents> createComponentsTypeDeduction(Payrolls payrolls) {
        log.info("Create PayrollComponents type DEDUCTION for payroll ID: {}", payrolls.getId());
        Objects.requireNonNull(payrolls.getEmployee(), "Employee cannot be null");
        Objects.requireNonNull(payrolls.getPayPeriod(), "Pay period cannot be null");

        List<DisciplinaryActionDTO> disciplinaryActions = disciplinaryActionService.getDisciplinaryActionByEmployeeIdAndDate(
                payrolls.getEmployee().getId(),
                payrolls.getPayPeriod().getStartDate(),
                payrolls.getPayPeriod().getEndDate());

        return createComponentsDeductionReturnListDTO(payrolls, disciplinaryActions);
    }

    protected List<PayrollComponents> createComponentsDeductionReturnListDTO(Payrolls payrolls, List<DisciplinaryActionDTO> disciplinaryActions) {
        Set<Integer> regulationIds = disciplinaryActions.stream()
                .map(DisciplinaryActionDTO::getRegulationId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Integer, Regulations> regulationMap = regulationIds.isEmpty() ? Map.of() :
                regulationsService.findAllById(regulationIds).stream()
                        .collect(Collectors.toMap(Regulations::getId, r -> r));


        return disciplinaryActions.stream()
                .map(action -> {
                    Objects.requireNonNull(action.getDescription(), "Disciplinary action description cannot be null");
                    if (action.getPenaltyAmount() == null && action.getRegulationId() == null) {
                        throw new CustomException(Error.REGULATION_INVALID_AMOUNT);
                    }

                    PayrollComponents.PayrollComponentsBuilder builder = PayrollComponents.builder()
                            .name(action.getDescription())
                            .type(PayrollComponentType.DEDUCTION)
                            .payroll(payrolls);

                    if (action.getRegulationId() != null) {
                        Regulations regulation = regulationMap.get(action.getRegulationId());
                        if (regulation == null) {
                            throw new CustomException(Error.REGULATION_NOT_FOUND);
                        }
                        if (action.getPenaltyAmount() == null && regulation.getAmount() == null &&
                                regulation.getPercentage() == null) {
                            throw new CustomException(Error.REGULATION_INVALID_AMOUNT);
                        }
                        builder.regulation(regulation)
                                .amount(action.getPenaltyAmount() != null ? action.getPenaltyAmount() : regulation.getAmount())
                                .percentage(regulation.getPercentage())
                                .isPercentage(regulation.getPercentage() != null);
                    } else {
                        builder.amount(action.getPenaltyAmount())
                                .isPercentage(false)
                                .percentage(null)
                                .regulation(null);
                    }
                    return builder.build();
                })
                .toList();
    }

    protected List<PayrollComponents> createComponentsTypeDeductionWithPreloadedData(Payrolls payrolls, List<DisciplinaryActionDTO> disciplinaryActions) {
        log.info("Create PayrollComponents type DEDUCTION for payroll ID: {} with preloaded data", payrolls.getId());
        Objects.requireNonNull(payrolls.getEmployee(), "Employee cannot be null");
        Objects.requireNonNull(payrolls.getPayPeriod(), "Pay period cannot be null");

        return createComponentsDeductionReturnListDTO(payrolls, disciplinaryActions);
    }

    protected List<PayrollComponents> createComponentsTypeRewards(Payrolls payrolls) {
        log.info("Create PayrollComponents type REWARD for payroll ID: {}", payrolls.getId());
        Objects.requireNonNull(payrolls.getEmployee(), "Employee cannot be null");
        Objects.requireNonNull(payrolls.getPayPeriod(), "Pay period cannot be null");

        List<RewardDTO> rewards = rewardService.getRewardByEmployeeIdAndDate(
                payrolls.getEmployee().getId(),
                payrolls.getPayPeriod().getStartDate(),
                payrolls.getPayPeriod().getEndDate());

        return createComponentsRewardReturnListDTO(payrolls, rewards);
    }

    protected List<PayrollComponents> createComponentsTypeRewardsWithPreloadedData(Payrolls payrolls, List<RewardDTO> rewards) {
        log.info("Create PayrollComponents type REWARD for payroll ID: {} with preloaded data", payrolls.getId());
        Objects.requireNonNull(payrolls.getEmployee(), "Employee cannot be null");
        Objects.requireNonNull(payrolls.getPayPeriod(), "Pay period cannot be null");

        return createComponentsRewardReturnListDTO(payrolls, rewards);
    }

    protected List<PayrollComponents> createComponentsRewardReturnListDTO(Payrolls payrolls, List<RewardDTO> rewards) {
        return rewards.stream()
                .map(reward -> {
                    Objects.requireNonNull(reward.getTitle(), "Reward title cannot be null");
                    if (reward.getRewardAmount() == null && reward.getPercentage() == null) {
                        throw new CustomException(Error.REGULATION_INVALID_AMOUNT);
                    }
                    return PayrollComponents.builder()
                            .name(reward.getTitle())
                            .type(PayrollComponentType.REWARD)
                            .payroll(payrolls)
                            .amount(reward.getRewardAmount())
                            .isPercentage(Boolean.TRUE.equals(reward.getIsPercentage()))
                            .percentage(reward.getPercentage())
                            .regulation(null)
                            .build();
                })
                .toList();
    }
}
