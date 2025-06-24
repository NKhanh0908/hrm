package com.project.hrm.repositories;

import com.project.hrm.entities.DayOff;
import com.project.hrm.entities.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Integer>, JpaSpecificationExecutor<DayOff> {
    @Query(value = "SELECT * FROM day_off WHERE employee_id = :employeeId", nativeQuery = true)
    List<DayOff> findDayOffsByEmployeeIdNative(@Param("employeeId") Integer employeeId);
}
