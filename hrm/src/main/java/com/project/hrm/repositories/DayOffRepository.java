package com.project.hrm.repositories;

import com.project.hrm.entities.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Integer> {
}
