package com.project.hrm.payroll.services;

import com.project.hrm.payroll.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsUpdateDTO;
import com.project.hrm.payroll.entities.Regulations;
import com.project.hrm.payroll.enums.PayrollComponentType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegulationsService {

    List<RegulationsDTO> filter(RegulationsFilter filter, int page, int size);

    RegulationsDTO getById(Integer id);

    Regulations getEntityById(Integer id);

    Boolean checkExists(Integer regulationsId);

    RegulationsDTO create(RegulationsCreateDTO regulationsCreateDTO);

    RegulationsDTO update(RegulationsUpdateDTO regulationsUpdateDTO);

    void delete(Integer regulationsId);

    Regulations getRegulationsByKey(String regulationsKey);

    List<Regulations> findRegulationByType(PayrollComponentType type);

    List<Regulations> findAllById(Iterable<Integer> ids);
}
