package com.project.hrm.training.service.impl;

import com.project.hrm.auth.service.AccountService;
import com.project.hrm.common.service.RedisService;
import com.project.hrm.department.service.RoleService;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramUpdateDTO;
import com.project.hrm.department.entity.Role;
import com.project.hrm.training.entity.TrainingProgram;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.training.mapper.TrainingProgramMapper;
import com.project.hrm.common.redis.RedisKeys;
import com.project.hrm.training.repository.TrainingProgramRepository;
import com.project.hrm.training.specification.TrainingProgramSpecification;
import com.project.hrm.training.service.TrainingProgramService;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Slf4j
@AllArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;

    private final RoleService roleService;
    private final AccountService accountService;
    private final RedisService redisService;

    private final TrainingProgramMapper trainingProgramMapper;

    /**
     * Creates a new training program based on the provided data.
     *
     * @param trainingProgramCreateDTO the training program creation data.
     * @return the created {@link TrainingProgramDTO}.
     */
    @Transactional
    @Override
    public TrainingProgramDTO create(TrainingProgramCreateDTO trainingProgramCreateDTO) {
        log.info("Creating TrainingProgram with ID");

        TrainingProgram trainingProgram = trainingProgramMapper.convertCreateDTOToEntity(trainingProgramCreateDTO);

        Role role = roleService.getEntityById(trainingProgramCreateDTO.getRoleId());

        trainingProgram.setId(IdGenerator.getGenerationId());
        trainingProgram.setCreateBy(accountService.getPrincipal());
        trainingProgram.setTargetRole(role);

        return trainingProgramMapper.convertToDTO(trainingProgramRepository.save(trainingProgram));
    }

    /**
     * Updates an existing training program based on the information provided in {@link TrainingProgramUpdateDTO}.
     * This method retrieves the entity, applies non-null changes from the DTO, and persists the updated entity.
     *
     * @param trainingProgramUpdateDTO the DTO containing the fields to be updated (must include the program ID)
     * @return a {@link TrainingProgramDTO} representing the training program after the update
     */
    @Transactional
    @Override
    public TrainingProgramDTO update(TrainingProgramUpdateDTO trainingProgramUpdateDTO) {
        log.info("Updating TrainingProgram with ID: {}", trainingProgramUpdateDTO.getId());

        TrainingProgram program = getEntityById(trainingProgramUpdateDTO.getId());

        if (trainingProgramUpdateDTO.getTitle() != null) {
            program.setTitle(trainingProgramUpdateDTO.getTitle());
        }
        if (trainingProgramUpdateDTO.getDescription() != null) {
            program.setDescription(trainingProgramUpdateDTO.getDescription());
        }
        if (trainingProgramUpdateDTO.getCreateAt() != null) {
            program.setCreateAt(trainingProgramUpdateDTO.getCreateAt());
        }
        if (trainingProgramUpdateDTO.getMaterials() != null) {
            program.setMaterials(trainingProgramUpdateDTO.getMaterials());
        }
        if (trainingProgramUpdateDTO.getPrerequisites() != null) {
            program.setPrerequisites(trainingProgramUpdateDTO.getPrerequisites());
        }
        if (trainingProgramUpdateDTO.getIsMandatory() != null) {
            program.setIsMandatory(trainingProgramUpdateDTO.getIsMandatory());
        }

        TrainingProgram updated = trainingProgramRepository.save(program);

        TrainingProgramDTO result = trainingProgramMapper.convertToDTO(updated);

        String cacheKey = RedisKeys.trainingProgramKey(result.getId());
        if(redisService.hasKey(cacheKey)){
            redisService.del(cacheKey);
        }

        return trainingProgramMapper.convertToDTO(updated);
    }

    /**
     * Retrieves a {@link TrainingProgram} entity by its ID.
     *
     * @param id the ID of the training program.
     * @return the found {@link TrainingProgram} entity.
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingProgram getEntityById(Integer id) {
        log.info("Get training program by id: {}", id);

        return trainingProgramRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.TRAINING_PROGRAM_NOT_FOUND));
    }

    /**
     * Retrieves a {@link TrainingProgramDTO} by its ID.
     *
     * @param id the ID of the training program.
     * @return the found {@link TrainingProgramDTO}.
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingProgramDTO getDTOById(Integer id) {
        String cacheKey = RedisKeys.trainingProgramKey(id);

        TrainingProgramDTO cached = redisService.get(cacheKey, TrainingProgramDTO.class);
        if (cached != null) {
            log.info("Retrieved training program from cache: {}", id);
            return cached;
        }

        TrainingProgramDTO result = trainingProgramMapper.convertToDTO(getEntityById(id));

        redisService.set(cacheKey, result, Duration.ofMinutes(10));

        return result;
    }

    /**
     * Filters training programs based on filter criteria and paginates the result.
     *
     * @param trainingProgramFilter the object containing filter fields.
     * @param page                  the zero-based page index.
     * @param size                  the number of records per page.
     * @return list of {@link TrainingProgramDTO} matching the filter.
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<TrainingProgramDTO> filter(TrainingProgramFilter trainingProgramFilter, int page, int size) {
        log.info("Filtering training programs: {}", trainingProgramFilter);

        String cacheKey = String.format("%s:page:%d:size:%d:filter:%s",
                RedisKeys.TRAINING_PROGRAMS_LIST, page, size, trainingProgramFilter.toString());

        PageDTO<TrainingProgramDTO> cache = redisService.get(cacheKey, PageDTO.class);
        if(cache != null){
            log.info("Retrieved training programs from cache: {}", cacheKey);
            return cache;
        }

        Specification<TrainingProgram> trainingProgramSpecification = TrainingProgramSpecification.filter((trainingProgramFilter));

        Pageable pageable = PageRequest.of(page, size);

        Page<TrainingProgram> trainingProgramPage = trainingProgramRepository.findAll(trainingProgramSpecification, pageable);

        PageDTO<TrainingProgramDTO> result = trainingProgramMapper.toTrainingProgramPageDTO(trainingProgramPage);

        redisService.set(cacheKey, result, Duration.ofMinutes(10));

        return result;
    }
}
