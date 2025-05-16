package com.project.hrm.specifications;


import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.entities.Employees;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employees> filterEmployee(EmployeeFilter employeeFilter){
        return(root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if(!employeeFilter.getName().isEmpty()){
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.like(root.get("firstName"), "%"+ employeeFilter.getName()+ "%"),
                        criteriaBuilder.like(root.get("lastName"),"%" + employeeFilter.getName()+ "%")
                        )
                );
            }
            if(!employeeFilter.getEmail().isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + employeeFilter.getEmail()+ "%" ));
            }

            if(!employeeFilter.getGender().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("gender"), employeeFilter.getGender()));
            }

            if(!employeeFilter.getAddress().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("address"), employeeFilter.getAddress()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
