package com.project.hrm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import com.project.hrm.dto.salaryDTO.SalaryCreateDTO;
import com.project.hrm.dto.salaryDTO.SalaryDTO;
import com.project.hrm.dto.salaryDTO.SalaryUpdateDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Salary;
import com.project.hrm.mapper.SalaryMapper;
import com.project.hrm.repositories.SalaryRepository;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.SalaryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService{

    private final EmployeeService employeeService;
    private final SalaryRepository salaryRepository;
    private final SalaryMapper salaryMapper;

    /**
     * Retrieves all {@link Salary} entities.
     *
     * @return a list of {@link SalaryDTO}
     */
    @Override
    public List<SalaryDTO> getAll() {
        log.info("Fetching all salaries");

        List<Salary> list = salaryRepository.findAll();

        return list.stream()
                .map(salaryMapper::toSalaryDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a {@link Salary} entity by its ID.
     *
     * @param id the ID of the salary
     * @return the corresponding {@link SalaryDTO}
     * @throws CustomException if the salary is not found
     */
    @Override
    public SalaryDTO getById(Integer id) {
        log.info("Fetching salary by ID: {}", id);

        return salaryMapper.toSalaryDTO(
                salaryRepository.findById(id)
                        .orElseThrow(() -> new CustomException(Error.SALARY_NOT_FOUND))
        );
    }

    /**
     * Checks if a salary with the specified ID exists.
     *
     * @param salaryId the ID to check
     * @return {@code true} if it exists, otherwise {@code false}
     */
    @Override
    public Boolean checkExists(Integer salaryId) {
        log.info("Checking existence of salary ID: {}", salaryId);
        return salaryRepository.existsById(salaryId);
    }

    /**
     * Creates a new {@link Salary} entity from the provided {@link SalaryCreateDTO}.
     *
     * @param salaryCreateDTO the DTO containing data required to create a salary
     * @return the created {@link SalaryDTO} after being persisted
     */
    @Transactional
    @Override
    public SalaryDTO create(SalaryCreateDTO salaryCreateDTO) {
        log.info("Creating salary");

        Employees employee = employeeService.getEntityById(salaryCreateDTO.getEmployeeId());
        Salary salary = salaryMapper.toSalaryFromCreateDTO(salaryCreateDTO, employee);

        salary.setId(IdGenerator.getGenerationId());

        Salary saved = salaryRepository.save(salary);
        log.info("Salary created with ID: {}", saved.getId());

        return salaryMapper.toSalaryDTO(saved);
    }

    /**
     * Updates an existing {@link Salary} entity with data from {@link SalaryUpdateDTO}.
     *
     * @param salaryUpdateDTO the DTO containing fields to update
     * @return the updated {@link SalaryDTO}
     */
    @Transactional
    @Override
    public SalaryDTO update(SalaryUpdateDTO salaryUpdateDTO) {
        log.info("Updating salary with ID: {}", salaryUpdateDTO.getId());

        Salary salary = salaryMapper.toSalary(getById(salaryUpdateDTO.getId()));

        if (salaryUpdateDTO.getTime() != null) {
            salary.setTime(salaryUpdateDTO.getTime());
        }

        if (salaryUpdateDTO.getEmployeeId() != null) {
            salary.setEmployee(employeeService.getEntityById(salaryUpdateDTO.getEmployeeId()));
        }

        Salary saved = salaryRepository.save(salary);
        log.info("Salary updated with ID: {}", saved.getId());

        return salaryMapper.toSalaryDTO(saved);
    }

    /**
     * Deletes the {@link Salary} entity with the given ID.
     *
     * @param salaryId the ID of the salary to delete
     * @throws RuntimeException if the salary is not found
     */
    @Transactional
    @Override
    public void delete(Integer salaryId) {
        log.info("Deleting salary with ID: {}", salaryId);

        if (checkExists(salaryId)) {
            salaryRepository.deleteById(salaryId);
            log.info("Salary deleted with ID: {}", salaryId);
        } else {
            log.warn("Salary with ID {} not found", salaryId);
            throw new CustomException(Error.SALARY_NOT_FOUND);
        }
    }

    /**
     * Gets the {@link Salary} entity by ID for internal use.
     *
     * @param id the ID of the salary
     * @return the {@link Salary} entity
     * @throws CustomException if not found
     */
    @Override
    public Salary getEntityById(Integer id) {
        log.info("Getting salary entity by ID: {}", id);
        return salaryRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.SALARY_NOT_FOUND));
    }
}
