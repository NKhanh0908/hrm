package com.project.hrm.employee.service.impl;

import com.project.hrm.employee.dto.rewardDTO.RewardCreateDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardUpdateDTO;
import com.project.hrm.employee.entity.Reward;
import com.project.hrm.employee.mapper.RewardMapper;
import com.project.hrm.employee.repository.RewardRepository;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.employee.service.RewardService;
import com.project.hrm.common.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;
    private final RewardMapper rewardMapper;
    private final EmployeeService employeeService;

    @Transactional
    @Override
    public RewardDTO createReward(RewardCreateDTO rewardCreateDTO) {
        log.info("Create Reward: {}", rewardCreateDTO);

        Reward reward = rewardMapper.toEntityFromCreateDTO(rewardCreateDTO);

        reward.setId(IdGenerator.getGenerationId());

        reward.setEmployee(employeeService.getEntityById(rewardCreateDTO.getEmployeeId()));
        return rewardMapper.toDTO(rewardRepository.save(reward));
    }

    @Transactional
    @Override
    public RewardDTO updateReward(RewardUpdateDTO rewardUpdateDTO) {
        log.info("Update Reward: {}", rewardUpdateDTO);

        Reward reward = getEntity(rewardUpdateDTO.getId());

        if (rewardUpdateDTO.getTitle() != null && !rewardUpdateDTO.getTitle().isEmpty()) {
            reward.setTitle(rewardUpdateDTO.getTitle());
        }

        if (reward.getReason() != null && !rewardUpdateDTO.getReason().isEmpty()) {
            reward.setReason(rewardUpdateDTO.getReason());
        }

        if (rewardUpdateDTO.getRewardAmount() != null ){
            reward.setRewardAmount(rewardUpdateDTO.getRewardAmount());
        } else reward.setRewardAmount(null);
        if (rewardUpdateDTO.getRewardDate() != null ){
            reward.setRewardDate(rewardUpdateDTO.getRewardDate());
        }

        if (rewardUpdateDTO.getIsPercentage() != null ){
            reward.setIsPercentage(rewardUpdateDTO.getIsPercentage());
        } else reward.setIsPercentage(null);

        if (rewardUpdateDTO.getRewardDate() != null ){
            reward.setRewardDate(rewardUpdateDTO.getRewardDate());
        }else reward.setRewardDate(null);

        if (rewardUpdateDTO.getEmployeeId() != null && employeeService.checkExists(rewardUpdateDTO.getEmployeeId())) {
            reward.setEmployee(employeeService.getEntityById(rewardUpdateDTO.getEmployeeId()));
        }
        return rewardMapper.toDTO(rewardRepository.save(reward));
    }

    @Transactional
    @Override
    public void deleteReward(Integer id) {
        log.info("Deleting reward with id {}", id);
        if (checkExist(id)){
            rewardRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Reward with id " + id + " not found");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public RewardDTO getDTO(Integer id) {
        log.info("Get Reward DTO By ID {}", id);
        return rewardMapper.toDTO(getEntity(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Reward getEntity(Integer id) {
        log.info("Get Reward Entity with id: {}", id);
        return rewardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reward Entity with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExist(Integer id) {
        log.info("Check exist reward with id: {}", id);
        return rewardRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RewardDTO> getRewardByEmployeeIdAndDate(Integer id, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Get Reward By Employee Id And Date: {}, {} - {}", id, startDate, endDate);
        List<Reward> rewardList = rewardRepository.findAllByEmployeeIdAndRewardDateBetween(id, startDate, endDate);
        return rewardList.stream()
                .map(rewardMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Integer, List<RewardDTO>> getBatchRewards(List<Integer> employeeIds, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting batch rewards for {} employees", employeeIds.size());
        Map<Integer, List<RewardDTO>> result = new java.util.HashMap<>();

        List<Object[]> batchResults = rewardRepository.getBatchRewards(employeeIds, startDate, endDate);

        for (Object[] row : batchResults) {
            Integer employeeId = (Integer) row[0];
            Reward reward = (Reward) row[1];

            result.computeIfAbsent(employeeId, k -> new java.util.ArrayList<>())
                    .add(rewardMapper.toDTO(reward));
        }

        return result;
    }
}
