package com.project.hrm.employee.repository;

import com.project.hrm.employee.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Integer>, JpaSpecificationExecutor<Reward> {
    List<Reward> findAllByEmployeeIdAndRewardDateBetween(Integer employeeId, LocalDateTime rewardDateAfter, LocalDateTime rewardDateBefore);

    @Query("SELECT r.employee.id as employeeId, r " +
            "FROM Reward r WHERE r.employee.id IN :employeeIds " +
            "AND r.rewardDate BETWEEN :startDate AND :endDate")
    List<Object[]> getBatchRewards(@Param("employeeIds") List<Integer> employeeIds,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

}
