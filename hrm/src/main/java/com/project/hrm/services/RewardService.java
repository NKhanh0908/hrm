package com.project.hrm.services;

import com.project.hrm.dto.rewardDTO.RewardCreateDTO;
import com.project.hrm.dto.rewardDTO.RewardDTO;
import com.project.hrm.dto.rewardDTO.RewardUpdateDTO;
import com.project.hrm.entities.Reward;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface RewardService {

    RewardDTO createReward(RewardCreateDTO rewardCreateDTO);

    RewardDTO updateReward(RewardUpdateDTO rewardUpdateDTO);

    void deleteReward(Integer id);

    RewardDTO getDTo(Integer id);

    Reward getEntity(Integer id);

    Boolean checkExist(Integer id);

    List<RewardDTO> getRewardByEmployeeIdAndDate(Integer id, LocalDateTime startDate, LocalDateTime endDate);

}
