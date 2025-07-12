package com.project.hrm.recruitment.dto.recruitmentDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentUpdateDTO {

    @NotNull(message = "Recruitment ID is required")
    private Integer id;

    @NotBlank(message = "Contact phone is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Contact phone must be between 10 and 15 digits")
    private String contactPhone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be a future date")
    private LocalDateTime deadline;

    @NotBlank(message = "Job description is required")
    @Size(max = 2000, message = "Job description must not exceed 2000 characters")
    private String jobDescription;

    @NotNull(message = "Recruitment requirement ID is required")
    private Integer recruitmentRequirementId;
}
