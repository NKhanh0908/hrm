package com.project.hrm.department.repository;

import com.project.hrm.department.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByDepartments_Id(Integer id);
}
