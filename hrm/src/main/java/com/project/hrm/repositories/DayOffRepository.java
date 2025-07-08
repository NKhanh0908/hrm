package com.project.hrm.repositories;

import com.project.hrm.entities.DayOff;
import com.project.hrm.entities.Dependent;
import com.project.hrm.enums.DayOffStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Integer>, JpaSpecificationExecutor<DayOff> {
    @Query("SELECT d FROM DayOff d WHERE d.employee.id = :employeeId")
    List<DayOff> findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("SELECT DISTINCT d.startDate FROM DayOff d WHERE d.employee.id = :employeeId AND d.startDate <= :endDate AND d.endDate >= :startDate")
    List<LocalDate> findDistinctDaysOffByEmployeeId(@Param("employeeId") Integer employeeId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DISTINCT d.startDate FROM DayOff d WHERE d.employee.id = :employeeId AND d.startDate <= :endDate AND d.endDate >= :startDate AND d.status = :status")
    List<LocalDate> findDistinctDaysOffByEmployeeIdAndStatus(@Param("employeeId") Integer employeeId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("status") DayOffStatus status);

    @Query("SELECT d.employee.id as employeeId, COUNT(DISTINCT d.startDate) as dayOffCount " +
            "FROM DayOff d WHERE d.employee.id IN :employeeIds " +
            "AND d.startDate <= :endDate AND d.endDate >= :startDate " +
            "GROUP BY d.employee.id")
    List<Object[]> getBatchDayOffCount(@Param("employeeIds") List<Integer> employeeIds,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    @Query("SELECT d.employee.id as employeeId, COUNT(DISTINCT d.startDate) as dayOffNotAcceptCount " +
            "FROM DayOff d WHERE d.employee.id IN :employeeIds " +
            "AND d.startDate <= :endDate AND d.endDate >= :startDate " +
            "AND d.status = :status " +
            "GROUP BY d.employee.id")
    List<Object[]> getBatchDayOffNotAcceptCount(@Param("employeeIds") List<Integer> employeeIds,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate,
                                                @Param("status") DayOffStatus status);

}
