package com.project.hrm.payrollManager.configuration;

import com.project.hrm.payrollManager.entities.Regulations;
import com.project.hrm.payrollManager.enums.PayrollComponentType;
import com.project.hrm.payrollManager.repositories.RegulationsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegulationsInitializer {
    private final RegulationsRepository regulationsRepository;

    @PostConstruct
    public void initDefaultRegulations() {
        Map<Integer, RegulationData> defaultRegulations = getRegulationDataMap();
        List<Integer> ids = new ArrayList<>(defaultRegulations.keySet());
        List<Regulations> existingRegulations = regulationsRepository.findAllById(ids);
        Set<Integer> existingIds = existingRegulations.stream()
                .map(Regulations::getId)
                .collect(Collectors.toSet());

        List<Regulations> newRegulations = defaultRegulations.entrySet().stream()
                .filter(entry -> !existingIds.contains(entry.getKey()))
                .map(entry -> {
                    RegulationData data = entry.getValue();
                    return Regulations.builder()
                            .id(entry.getKey())
                            .regulationKey(data.key())
                            .name(data.name())
                            .type(data.Type())
                            .amount(data.amount())
                            .percentage(data.percentage())
                            .applicableSalary(data.applicableSalary())
                            .effectiveDate(data.effectiveDate())
                            .build();
                })
                .toList();

        if (!newRegulations.isEmpty()) {
            regulationsRepository.saveAll(newRegulations);
        }
    }

    private static Map<Integer, RegulationData> getRegulationDataMap() {
        Map<Integer, RegulationData> defaultRegulations = new HashMap<>();
        defaultRegulations.put(1, new RegulationData("SOCIAL_INSURANCE",
                "Bảo hiểm xã hội", PayrollComponentType.INSURANCE, null, 8F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        defaultRegulations.put(2, new RegulationData("HEALTH_INSURANCE",
                "Bảo hiểm y tế", PayrollComponentType.INSURANCE, null, 1.5F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        defaultRegulations.put(3, new RegulationData("UNEMOPLOYMENT_INSURANCE",
                "Bảo hiểm thất nghiệp", PayrollComponentType.INSURANCE, null, 1F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        defaultRegulations.put(4, new RegulationData("UNION_DUES",
                "Kinh phí công đoàn", PayrollComponentType.INSURANCE, null, 2F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        defaultRegulations.put(5, new RegulationData("PERSONAL_INCOME_TAX",
                "Thuế thu nhập cá nhân", PayrollComponentType.TAX, null, null, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        defaultRegulations.put(6, new RegulationData("ATTENDANCE_LATE",
                "Chấm công trễ", PayrollComponentType.DEDUCTION, BigDecimal.valueOf(50000), null, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        return defaultRegulations;
    }

    private record RegulationData(
            String key, String name, PayrollComponentType Type,
            BigDecimal amount, Float percentage, BigDecimal applicableSalary,
            LocalDateTime effectiveDate) {}
}
