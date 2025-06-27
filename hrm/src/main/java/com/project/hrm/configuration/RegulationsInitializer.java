package com.project.hrm.configuration;

import com.project.hrm.entities.Regulations;
import com.project.hrm.repositories.RegulationsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegulationsInitializer {
    private final RegulationsRepository regulationsRepository;

    @PostConstruct
    public void initDefaultRegulations() {
        Map<Integer, RegulationData> defaultRegulations = getRegulationDataMap();
        for (Map.Entry<Integer, RegulationData> entry : defaultRegulations.entrySet()) {
            Integer id = entry.getKey();
            RegulationData data = entry.getValue();

            regulationsRepository.findById(id).ifPresentOrElse(
                    existing -> {
                         //Không làm gì nếu đã tồn tại
                    },
                    () -> {
                        Regulations regulation = new Regulations();
                        regulation.setId(id);
                        regulation.setRegulationKey(data.key());
                        regulation.setName(data.name());
                        regulation.setAmount(data.amount());
                        regulation.setPercentage(data.percentage());
                        regulation.setApplicableSalary(data.applicableSalary());
                        regulation.setEffectiveDate(data.effectiveDate());
                        regulationsRepository.save(regulation);
                    }
            );
        }
    }

    private static Map<Integer, RegulationData> getRegulationDataMap() {
        Map<Integer, RegulationData> defaultRegulations = new HashMap<>();

        defaultRegulations.put(1, new RegulationData("SOCIAL_INSURANCE",
                "Bảo hiểm xã hội", null, 8F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)
        ));

        defaultRegulations.put(2, new RegulationData("HEALTH_INSURANCE",
                "Bảo hiểm y tế", null, 1.5F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)
        ));

        defaultRegulations.put(3, new RegulationData("UNEMOPLOYMENT_INSURANCE",
                "Bảo hiểm thất nghiệp", null, 1f, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)
        ));

        defaultRegulations.put(4, new RegulationData("UNION_DUES",
                "Kinh phí công đoàn", null, 2F, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)
        ));

        defaultRegulations.put(5, new RegulationData("PERSONAL_INCOME_TAX",
                "Thuế thu nhập cá nhân", null , null, null,
                LocalDateTime.of(2025, 1, 1, 0, 0)
        ));
        return defaultRegulations;
    }


    private record RegulationData(
            String key,
            String name,
            BigDecimal amount,
            Float percentage,
            BigDecimal applicableSalary,
            LocalDateTime effectiveDate
    ) {
    }
}
