package com.project.hrm.services;

import com.project.hrm.entities.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getEntityById(Integer id);
}
