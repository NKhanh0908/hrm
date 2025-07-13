package com.project.hrm.employee.specification;

import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.employee.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.employee.enums.EmployeeStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employees> filterEmployee(EmployeeFilter employeeFilter){
        return(root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if(employeeFilter.getName() != null && !employeeFilter.getName().isEmpty()){
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("firstName"), "%"+ employeeFilter.getName()+ "%"),
                        criteriaBuilder.like(root.get("lastName"),"%" + employeeFilter.getName()+ "%")
                        )
                );
            }
            if(employeeFilter.getEmail() !=null && !employeeFilter.getEmail().isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + employeeFilter.getEmail()+ "%" ));
            }

            if(employeeFilter.getStatus() != null && !employeeFilter.getStatus().isEmpty()){
                try {
                    EmployeeStatus status = EmployeeStatus.valueOf(employeeFilter.getStatus());
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                } catch (IllegalArgumentException ex) {
                    throw new CustomException(Error.INVALID_ENUM_VALUE);
                }

            }

            if(employeeFilter.getGender()!= null && !employeeFilter.getGender().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("gender"), employeeFilter.getGender()));
            }

            if(employeeFilter.getAddress() != null){
                predicates.add(criteriaBuilder.equal(root.get("address"), employeeFilter.getAddress()));
            }

            if (employeeFilter.getRoleId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("role").get("id"), employeeFilter.getRoleId()));
            }

            if (employeeFilter.getDepartmentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("role").get("departments").get("id"), employeeFilter.getDepartmentId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
