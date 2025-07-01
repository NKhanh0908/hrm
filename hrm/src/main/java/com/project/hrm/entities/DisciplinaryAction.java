package com.project.hrm.entities;

import com.project.hrm.enums.ViolationSeverity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DisciplinaryAction {
    @Id
    private Integer id;

    private String description; // Ví dụ: "Đi làm trễ", "Vi phạm nội quy phòng họp"
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employees employee;

    @ManyToOne
    @JoinColumn(name = "regulation_id")
    private Regulations regulation; // Regulation tương ứng (loại DEDUCTION)

    private BigDecimal penaltyAmount; // Trong trường hợp muốn override amount

    private Boolean resolved;

    @Enumerated(EnumType.STRING)
    private ViolationSeverity severity; // Enum: MINOR, MAJOR, CRITICAL
}
