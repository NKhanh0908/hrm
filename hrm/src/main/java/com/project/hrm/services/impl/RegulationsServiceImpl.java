package com.project.hrm.services.impl;

import com.project.hrm.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.dto.regulationsDTO.RegulationsUpdateDTO;
import com.project.hrm.entities.Regulations;
import com.project.hrm.mapper.RegulationsMapper;
import com.project.hrm.repositories.RegulationsRepository;
import com.project.hrm.services.RegulationsService;
import com.project.hrm.specifications.RegulationsSpecification;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RegulationsServiceImpl implements RegulationsService {

    private final RegulationsRepository regulationsRepository;
    private final RegulationsMapper regulationsMapper;

    @Transactional(readOnly = true)
    @Override
    public List<RegulationsDTO> filter(RegulationsFilter filter, int page, int size) {
        log.info("filter regulations");

        Specification<Regulations> spec = RegulationsSpecification.filterRegulationsSpecification(filter);

        Pageable pageable = PageRequest.of(page, size);

        return regulationsMapper.convertPageEntityToPageDTO(regulationsRepository.findAll(spec, pageable));
    }

    @Transactional(readOnly = true)
    @Override
    public RegulationsDTO getById(Integer id) {
        log.info("Get Regulations by id: {}",  id);
        return regulationsMapper.toRegulationsDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Regulations getEntityById(Integer id) {
        log.info("Get Regulations Entity by id: {}",  id);
        return regulationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Regulations not found by id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer regulationsId) {
        log.info("Check existence of Regulations with id: {}", regulationsId);
        return regulationsRepository.existsById(regulationsId);
    }

    @Transactional
    @Override
    public RegulationsDTO create(RegulationsCreateDTO regulationsCreateDTO) {
        log.info("Create Regulations DTO: {}", regulationsCreateDTO);

        Regulations regulations = regulationsMapper.toRegulationsFromCreateDTO(regulationsCreateDTO);
        regulations.setId(IdGenerator.getGenerationId());

        return regulationsMapper.toRegulationsDTO(regulationsRepository.save(regulations));
    }

    @Transactional
    @Override
    public RegulationsDTO update(RegulationsUpdateDTO regulationsUpdateDTO) {
        log.info("Update Regulations with ID: {}", regulationsUpdateDTO.getId());

        // Tìm regulations hiện có
        Regulations regulations = regulationsRepository.findById(regulationsUpdateDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Regulations not found with id: " + regulationsUpdateDTO.getId()));

        // Cập nhật các trường nếu không null
        if (regulationsUpdateDTO.getName() != null) {
            regulations.setName(regulationsUpdateDTO.getName());
        }
        if (regulationsUpdateDTO.getApplicable_salary() != null) {
            regulations.setApplicable_salary(regulationsUpdateDTO.getApplicable_salary());
        }
        if (regulationsUpdateDTO.getPercentage() != null) {
            regulations.setPercentage(regulationsUpdateDTO.getPercentage());
        }
        if (regulationsUpdateDTO.getAmount() != null) {
            regulations.setAmount(regulationsUpdateDTO.getAmount());
        }
        if (regulationsUpdateDTO.getEffective_date() != null) {
            regulations.setEffective_date(regulationsUpdateDTO.getEffective_date());
        }

        Regulations updated = regulationsRepository.save(regulations);
        return regulationsMapper.toRegulationsDTO(updated);
    }


    @Transactional
    @Override
    public void delete(Integer regulationsId) {
        log.info("Delete Regulations with id: {}", regulationsId);

        if (checkExists(regulationsId)) {
            regulationsRepository.deleteById(regulationsId);
        }else {
            throw new EntityNotFoundException("Regulations not found by id: " + regulationsId);
        }
    }
}
