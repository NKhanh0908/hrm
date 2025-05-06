package com.project.hrm.specifications;


import com.project.hrm.entities.Employees;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employees> filterEmployee(String name, String email, String gender, String address){
        return(root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if(!name.isEmpty()){
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.like(root.get("firstName"), "%"+ name+ "%"),
                        criteriaBuilder.like(root.get("lastName"),"%" + name+ "%")
                        )
                );
            }
            if(!email.isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email+ "%" ));
            }

            if(!gender.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }

            if(!address.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("address"), address));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
