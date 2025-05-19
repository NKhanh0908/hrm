package com.project.hrm.services.impl;

import com.project.hrm.entities.Role;
import com.project.hrm.repositories.RoleRepository;
import com.project.hrm.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role getEntityById(Integer id) {
        log.info("Find role entity by id: {}", id);

        return roleRepository.findById(id)
                .orElseThrow(
                        ()->{
                            log.info("Error not found by id: {}", id);

                            return new RuntimeException("Role not found");
                        }
                );
    }
}
