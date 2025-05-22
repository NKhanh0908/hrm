package com.project.hrm.dto.candidateProfileDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfileCreateDTO {

        @NotNull(message = "Name is required")
        private String name;

        @NotNull(message = "Email is required")
        @Email(message = "Email is not valid")
        private String email;

        @NotNull(message = "Phone number is required")
        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{9,11}$", message = "Phone number must be 9 to 11 digits, optionally starting with country code")
        private String phone;

        @NotNull(message = "CV link is required")
        @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "CV link must be a valid URL")
        private String linkCV;

        @NotNull(message = "Skills information is required")
        private String skills;

        @NotNull(message = "Experience information is required")
        private String experience;
}