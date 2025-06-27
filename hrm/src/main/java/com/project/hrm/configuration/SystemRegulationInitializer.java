package com.project.hrm.configuration;

import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.entities.SystemRegulation;
import com.project.hrm.repositories.SystemRegulationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SystemRegulationInitializer{
    private final SystemRegulationRepository systemRegulationRepository;

    @PostConstruct
    public void initDefaultSystemRegulations() {
        Map<SystemRegulationKey, RegulationValueDescription> defaultRegulations = new HashMap<>();

        defaultRegulations.put(SystemRegulationKey.CHECKIN_START_TIME, new RegulationValueDescription("08:00", "Thời gian bắt đầu làm việc mặc định"));
        defaultRegulations.put(SystemRegulationKey.CHECKOUT_END_TIME, new RegulationValueDescription("17:00", "Thời gian kết thúc làm việc mặc định"));
        defaultRegulations.put(SystemRegulationKey.OVERTIME_RATE, new RegulationValueDescription("2", "Hệ số tăng ca"));
        defaultRegulations.put(SystemRegulationKey.DEPENDENT_DEDUCTION, new RegulationValueDescription("4400000", "Khấu trừ thân nhân phụ thuộc"));
        defaultRegulations.put(SystemRegulationKey.SELF_DEDUCTION, new RegulationValueDescription("11000000","Khấu trừ đối với cá nhân nộp thuế"));
        defaultRegulations.put(SystemRegulationKey.WORKDAYS_PER_MONTH, new RegulationValueDescription("28", "Số ngày trong một tháng theo quy định"));

        for (Map.Entry<SystemRegulationKey, RegulationValueDescription> entry : defaultRegulations.entrySet()) {
            SystemRegulationKey key = entry.getKey();
            RegulationValueDescription valueDesc = entry.getValue();

            systemRegulationRepository.findById(key).ifPresentOrElse(
                existing -> {
                    // Không làm gì nếu đã tồn tại
                },
                () -> {
                    SystemRegulation regulation = new SystemRegulation();
                    regulation.setKey(key);
                    regulation.setValue(valueDesc.value());
                    regulation.setDescription(valueDesc.description());
                    systemRegulationRepository.save(regulation);
                }
            );
        }
    }

    private record RegulationValueDescription(String value, String description) {
    }
}
