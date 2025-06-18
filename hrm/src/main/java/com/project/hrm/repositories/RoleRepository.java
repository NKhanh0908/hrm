package com.project.hrm.repositories;

import com.project.hrm.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByDepartments_Id(Integer id);
}
