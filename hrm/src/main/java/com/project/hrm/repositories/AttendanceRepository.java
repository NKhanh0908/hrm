package com.project.hrm.repositories;

import com.project.hrm.entities.Attendance;
import com.project.hrm.entities.Employees;
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
    @Query("SELECT COUNT(a) > 0 FROM Attendance a WHERE " +
            "a.checkIn >= :startOfDay AND a.checkIn <= :endOfDay AND a.checkOut IS NULL")
    boolean existsCheckInOnDateWithoutCheckOut(@Param("startOfDay") LocalDateTime startOfDay,
                                               @Param("endOfDay") LocalDateTime endOfDay);

    Optional<Attendance> findFirstByEmployee_IdAndCheckOutIsNull(Integer employeeId);

    List<Attendance> findByEmployeeAndAttendanceDateBetween(Employees employee, LocalDateTime start, LocalDateTime end);
}
