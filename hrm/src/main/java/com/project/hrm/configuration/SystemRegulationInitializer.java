package com.project.hrm.configuration;

import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.entities.SystemRegulation;
import com.project.hrm.repositories.SystemRegulationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SystemRegulationInitializer {
    private final SystemRegulationRepository systemRegulationRepository;

    @PostConstruct
    public void initDefaultSystemRegulations() {
        Map<SystemRegulationKey, RegulationValueDescription> defaultRegulations = new HashMap<>();
        defaultRegulations.put(SystemRegulationKey.CHECKIN_START_TIME, new RegulationValueDescription("08:00", "Thời gian bắt đầu làm việc mặc định"));
        defaultRegulations.put(SystemRegulationKey.CHECKOUT_END_TIME, new RegulationValueDescription("17:00", "Thời gian kết thúc làm việc mặc định"));
        defaultRegulations.put(SystemRegulationKey.OVERTIME_RATE, new RegulationValueDescription("2", "Hệ số tăng ca"));
        defaultRegulations.put(SystemRegulationKey.DEPENDENT_DEDUCTION, new RegulationValueDescription("4400000", "Khấu trừ thân nhân phụ thuộc"));
        defaultRegulations.put(SystemRegulationKey.SELF_DEDUCTION, new RegulationValueDescription("11000000", "Khấu trừ đối với cá nhân nộp thuế"));
        defaultRegulations.put(SystemRegulationKey.WORKDAYS_PER_MONTH, new RegulationValueDescription("28", "Số ngày trong một tháng theo quy định"));
        defaultRegulations.put(SystemRegulationKey.HOURLY_PER_ONE_DAY, new RegulationValueDescription("8", "Số giờ làm chính thức trong một ngày"));

        List<SystemRegulationKey> keys = new ArrayList<>(defaultRegulations.keySet());
        List<SystemRegulation> existingRegulations = systemRegulationRepository.findAllById(keys);
        Set<SystemRegulationKey> existingKeys = existingRegulations.stream()
                .map(SystemRegulation::getKey)
                .collect(Collectors.toSet());

        List<SystemRegulation> newRegulations = defaultRegulations.entrySet().stream()
                .filter(entry -> !existingKeys.contains(entry.getKey()))
                .map(entry -> SystemRegulation.builder()
                        .key(entry.getKey())
                        .value(entry.getValue().value())
                        .description(entry.getValue().description())
                        .build())
                .toList();

        if (!newRegulations.isEmpty()) {
            systemRegulationRepository.saveAll(newRegulations);
        }
    }

    private record RegulationValueDescription(String value, String description) {}
}
