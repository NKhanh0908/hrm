package com.project.hrm.employee.repository;

import com.project.hrm.employee.entity.DisciplinaryAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DisciplinaryActionRepository extends JpaRepository<DisciplinaryAction, Integer>, JpaSpecificationExecutor<DisciplinaryAction> {
    List<DisciplinaryAction> findAllByEmployeeIdAndDateBetween(Integer employeeId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT da.employee.id as employeeId, da " +
            "FROM DisciplinaryAction da WHERE da.employee.id IN :employeeIds " +
            "AND da.date BETWEEN :startDate AND :endDate")
    List<Object[]> getBatchDisciplinaryActions(@Param("employeeIds") List<Integer> employeeIds,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

}
