package com.project.hrm.employee.mapper;

import com.project.hrm.employee.dto.rewardDTO.RewardCreateDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardDTO;
import com.project.hrm.employee.entity.Reward;
import org.springframework.stereotype.Component;

@Component
public class RewardMapper {
    public Reward toEntity(RewardDTO rewardDTO) {
        return Reward.builder()
                .id(rewardDTO.getId())
                .title(rewardDTO.getTitle())
                .reason(rewardDTO.getReason())
                .rewardDate(rewardDTO.getRewardDate())
                .isPercentage(rewardDTO.getIsPercentage())
                .percentage(rewardDTO.getPercentage())
                .rewardDate(rewardDTO.getRewardDate())
                .rewardAmount(rewardDTO.getRewardAmount())
                .appliedToPayroll(rewardDTO.getAppliedToPayroll())
                .build();
    }

    public RewardDTO toDTO(Reward reward) {
        return RewardDTO.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .reason(reward.getReason())
                .rewardDate(reward.getRewardDate())
                .rewardAmount(reward.getRewardAmount())
                .isPercentage(reward.getIsPercentage())
                .percentage(reward.getPercentage())
                .rewardDate(reward.getRewardDate())
                .appliedToPayroll(reward.getAppliedToPayroll())
                .employeeId(reward.getEmployee().getId())
                .build();
    }

    public Reward toEntityFromCreateDTO(RewardCreateDTO rewardCreateDTO) {
        return Reward.builder()
                .title(rewardCreateDTO.getTitle())
                .reason(rewardCreateDTO.getReason())
                .rewardDate(rewardCreateDTO.getRewardDate())
                .isPercentage(rewardCreateDTO.getIsPercentage())
                .percentage(rewardCreateDTO.getPercentage())
                .rewardDate(rewardCreateDTO.getRewardDate())
                .appliedToPayroll(rewardCreateDTO.getAppliedToPayroll())
                .build();
    }
}
