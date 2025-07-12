package com.project.hrm.employee.service;

import com.project.hrm.employee.dto.rewardDTO.RewardCreateDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardUpdateDTO;
import com.project.hrm.employee.entity.Reward;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface RewardService {

    RewardDTO createReward(RewardCreateDTO rewardCreateDTO);

    RewardDTO updateReward(RewardUpdateDTO rewardUpdateDTO);

    void deleteReward(Integer id);

    RewardDTO getDTO(Integer id);

    Reward getEntity(Integer id);

    Boolean checkExist(Integer id);

    List<RewardDTO> getRewardByEmployeeIdAndDate(Integer id, LocalDateTime startDate, LocalDateTime endDate);

    Map<Integer, List<RewardDTO>> getBatchRewards(List<Integer> employeeIds, LocalDateTime startDate, LocalDateTime endDate);


}
