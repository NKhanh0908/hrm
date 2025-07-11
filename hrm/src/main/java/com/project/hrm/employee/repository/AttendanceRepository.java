package com.project.hrm.employee.repository;

import com.project.hrm.employee.entity.Attendance;
import com.project.hrm.employee.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer>, JpaSpecificationExecutor<Attendance> {

    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.checkOut IS NULL ORDER BY a.checkIn DESC LIMIT 1")
    Optional<Attendance> findFirstByEmployeeIdAndCheckOutIsNull(@Param("employeeId") Integer employeeId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Attendance a WHERE a.employee.id = :employeeId AND a.checkIn >= :startOfDay AND a.checkIn <= :endOfDay AND a.checkOut IS NULL")
    boolean existsCheckInOnDateWithoutCheckOut(@Param("employeeId") Integer employeeId,
                                               @Param("startOfDay") LocalDateTime startOfDay,
                                               @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COALESCE(SUM(a.regularTime), 0) FROM Attendance a WHERE a.employee = :employee AND a.attendanceDate BETWEEN :startDate AND :endDate")
    float getTotalRegularTime(@Param("employee") Employees employee, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(a.otherTime), 0) FROM Attendance a WHERE a.employee = :employee AND a.attendanceDate BETWEEN :startDate AND :endDate")
    float getTotalOtherTime(@Param("employee") Employees employee, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a.employee.id as employeeId, COALESCE(SUM(a.regularTime), 0) as totalRegularTime " +
            "FROM Attendance a WHERE a.employee.id IN :employeeIds " +
            "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
            "GROUP BY a.employee.id")
    List<Object[]> getBatchTotalRegularTime(@Param("employeeIds") List<Integer> employeeIds,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a.employee.id as employeeId, COALESCE(SUM(a.otherTime), 0) as totalOtherTime " +
            "FROM Attendance a WHERE a.employee.id IN :employeeIds " +
            "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
            "GROUP BY a.employee.id")
    List<Object[]> getBatchTotalOtherTime(@Param("employeeIds") List<Integer> employeeIds,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
}
