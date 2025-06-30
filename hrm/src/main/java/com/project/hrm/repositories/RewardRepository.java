package com.project.hrm.repositories;

import com.project.hrm.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Integer>, JpaSpecificationExecutor<Reward> {
    List<Reward> findAllByEmployeeIdAndRewardDateBetween(Integer employeeId, LocalDateTime rewardDateAfter, LocalDateTime rewardDateBefore);
}
