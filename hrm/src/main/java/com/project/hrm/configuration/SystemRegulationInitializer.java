package com.project.hrm.configuration;

import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.entities.SystemRegulation;
import com.project.hrm.repositories.SystemRegulationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SystemRegulationInitializer{
    private final SystemRegulationRepository systemRegulationRepository;

    @PostConstruct
    public void initDefaultRegulations() {
        Map<SystemRegulationKey, RegulationValueDescription> defaultRegulations = new HashMap<>();

        defaultRegulations.put(SystemRegulationKey.CHECKIN_START_TIME, new RegulationValueDescription("08:00", "Thời gian bắt đầu làm việc mặc định"));
        defaultRegulations.put(SystemRegulationKey.CHECKOUT_END_TIME, new RegulationValueDescription("17:00", "Thời gian kết thúc làm việc mặc định"));
//        defaultRegulations.put(RegulationKey.WORKING_HOURS_PER_DAY, new RegulationValueDescription("8", "Tổng số giờ làm việc một ngày"));
//        defaultRegulations.put(RegulationKey.OVERTIME_LIMIT, new RegulationValueDescription("2", "Giới hạn số giờ tăng ca mỗi ngày"));

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
