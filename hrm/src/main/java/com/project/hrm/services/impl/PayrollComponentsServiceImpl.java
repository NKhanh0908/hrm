package com.project.hrm.services.impl;

import com.project.hrm.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.dto.payrollComponentsDTO.*;
import com.project.hrm.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.dto.rewardDTO.RewardDTO;
import com.project.hrm.entities.*;
import com.project.hrm.enums.PayrollComponentType;
import com.project.hrm.mapper.PayrollComponentMapper;
import com.project.hrm.mapper.RegulationsMapper;
import com.project.hrm.repositories.PayrollComponentsRepository;
import com.project.hrm.services.DisciplinaryActionService;
import com.project.hrm.services.PayrollComponentsService;
import com.project.hrm.services.RegulationsService;
import com.project.hrm.services.RewardService;
import com.project.hrm.specifications.PayrollComponentsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollComponentsServiceImpl implements PayrollComponentsService {
    private final PayrollComponentsRepository payrollComponentsRepository;
    private final PayrollComponentMapper payrollComponentMapper;
    private final RegulationsService regulationsService;
    private final RegulationsMapper regulationsMapper;
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
        payrollComponents.setId(IdGenerator.getGenerationId());

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

    @Override
    public List<PayrollComponentsDTO> createComponents(Payrolls payrolls) {
        log.info("Create Payroll Components With Regulations");
        RegulationsFilter regulationsFilter = new RegulationsFilter();

        regulationsFilter.setPayrollComponentType(PayrollComponentType.TAX);
        List<Regulations> regulationsTax = regulationsService.filter(regulationsFilter, Integer.MAX_VALUE, Integer.MAX_VALUE)
                .stream()
                .map(regulationsMapper::toRegulations)
                .toList();

        regulationsFilter.setPayrollComponentType(PayrollComponentType.INSURANCE);
        List<Regulations> regulationsInsurance = regulationsService.filter(regulationsFilter, Integer.MAX_VALUE, Integer.MAX_VALUE)
                .stream()
                .map(regulationsMapper::toRegulations)
                .toList();

        List<PayrollComponents> payrollComponentsListReward = createComponentsTypeRewards(payrolls);
        List<PayrollComponents> payrollComponentsListDeduction = createComponentsTypeDeduction(payrolls);
        List<PayrollComponents> payrollComponentsListTax = createComponentsWithRegulations(payrolls, regulationsTax);
        List<PayrollComponents> payrollComponentsListInsurance = createComponentsWithRegulations(payrolls, regulationsInsurance);


        List<PayrollComponents> componentsListReturn = Stream.of(payrollComponentsListTax, payrollComponentsListInsurance, payrollComponentsListReward, payrollComponentsListDeduction)
                .flatMap(Collection::stream)
                .toList();

        return componentsListReturn.stream()
                .map(payrollComponentMapper::toPayrollComponentsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PayrollComponents getPayrollIdAndType(Integer payrollId, PayrollComponentType type) {
        log.info("Get Payroll Components by Payroll id and type: {}, {}",payrollId, type);
        return payrollComponentsRepository.findByPayrollIdAndType(payrollId, type);
    }

    protected List<PayrollComponents> createComponentsWithRegulations(Payrolls payrolls,List<Regulations> regulations) {
        List<PayrollComponents> components = new ArrayList<>();

        for (Regulations regulation : regulations) {
            if (regulation.getType().equals(PayrollComponentType.TAX)) {
                PayrollComponents payrollComponents = new PayrollComponents();

                payrollComponents.setName(regulation.getName());
                payrollComponents.setType(regulation.getType());
                payrollComponents.setAmount(null);
                payrollComponents.setIsPercentage(true);
                payrollComponents.setPercentage(null);
                payrollComponents.setRegulation(regulation);
                payrollComponents.setPayroll(payrolls);

                components.add(payrollComponents);
            } else {
                PayrollComponents payrollComponents = new PayrollComponents();

                payrollComponents.setName(regulation.getName());
                payrollComponents.setType(regulation.getType());
                if (regulation.getAmount() != null) {
                    payrollComponents.setAmount(regulation.getAmount());
                } else payrollComponents.setAmount(null);

                if (regulation.getPercentage() != null) {
                    payrollComponents.setIsPercentage(true);
                    payrollComponents.setPercentage(regulation.getPercentage());
                } else {
                    payrollComponents.setIsPercentage(false);
                    payrollComponents.setPercentage(null);
                }
                payrollComponents.setRegulation(regulation);
                payrollComponents.setPayroll(payrolls);

                components.add(payrollComponents);
            }

        }
        return payrollComponentsRepository.saveAll(components);
    }



    private List<PayrollComponents> createComponentsTypeDeduction(Payrolls payrolls) {
        log.info("Create Payroll Components Type Deduction");

        List<DisciplinaryActionDTO> disciplinaryActionList = disciplinaryActionService.getDisciplinaryActionByEmployeeIdAndDate(payrolls.getEmployee().getId(), payrolls.getPayPeriod().getStartDate(), payrolls.getPayPeriod().getEndDate());

        List<PayrollComponents> payrollComponentsDeduction = new ArrayList<>();
        for (DisciplinaryActionDTO disciplinaryAction : disciplinaryActionList) {
            PayrollComponents payrollComponents = new PayrollComponents();

            payrollComponents.setName(disciplinaryAction.getDescription());
            payrollComponents.setType(PayrollComponentType.DEDUCTION);
            payrollComponents.setPayroll(payrolls);

            if (disciplinaryAction.getPenaltyAmount() != null) {
                payrollComponents.setAmount(disciplinaryAction.getPenaltyAmount());
            }

            if (disciplinaryAction.getRegulationId() != null) {
                Regulations regulation = regulationsService.getEntityById(disciplinaryAction.getRegulationId());

                payrollComponents.setRegulation(regulation);

                if (disciplinaryAction.getPenaltyAmount() == null) {
                    payrollComponents.setAmount(regulation.getAmount());
                }

                payrollComponents.setPercentage(regulation.getPercentage());
                payrollComponents.setIsPercentage(payrollComponents.getPercentage() != null);

            } else payrollComponents.setRegulation(null);

            payrollComponentsDeduction.add(payrollComponents);
        }
        return payrollComponentsRepository.saveAll(payrollComponentsDeduction);
    }

    private List<PayrollComponents> createComponentsTypeRewards(Payrolls payrolls) {
        log.info("Create Payroll Components Rewards");

        List<RewardDTO> rewards = rewardService.getRewardByEmployeeIdAndDate(payrolls.getEmployee().getId(), payrolls.getPayPeriod().getStartDate(), payrolls.getPayPeriod().getEndDate());

        List<PayrollComponents> payrollComponentsReward = new ArrayList<>();
        for (RewardDTO reward : rewards) {
            PayrollComponents payrollComponents = new PayrollComponents();

            payrollComponents.setName(reward.getTitle());
            payrollComponents.setType(PayrollComponentType.REWARD);
            payrollComponents.setPayroll(payrolls);

            if (reward.getRewardAmount() != null) {
                payrollComponents.setAmount(reward.getRewardAmount());
            }

            payrollComponents.setRegulation(null);
            payrollComponents.setIsPercentage(reward.getIsPercentage() != null);

            if (reward.getIsPercentage() != null) {
                payrollComponents.setPercentage(reward.getPercentage());
            } else payrollComponents.setPercentage(null);

            payrollComponentsReward.add(payrollComponents);

        }
        return payrollComponentsRepository.saveAll(payrollComponentsReward);
    }
}
