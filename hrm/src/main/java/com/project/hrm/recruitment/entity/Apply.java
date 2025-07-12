package com.project.hrm.recruitment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.recruitment.enums.ApplyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getInfoApply",
                procedureName = "getInfoApply",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id_employee", type = Integer.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = "getRoleIdByApplyId",
                procedureName = "get_role_id_by_apply_id",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "apply_id", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "role_id", type = Integer.class),
                }
        )
})

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apply {
    @Id
    private Integer id;
    private LocalDateTime applyAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplyStatus applyStatus;

    private String position;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private CandidateProfile candidateProfile;

}
