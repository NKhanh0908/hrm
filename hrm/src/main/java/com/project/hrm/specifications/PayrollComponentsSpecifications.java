    package com.project.hrm.specifications;

    import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
    import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilterWithRange;
    import com.project.hrm.dto.payrollsDTO.PayrollsFilterWithRange;
    import com.project.hrm.entities.PayrollComponents;
    import com.project.hrm.enums.PayrollComponentType;
    import jakarta.persistence.criteria.Predicate;
    import org.springframework.data.jpa.domain.Specification;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.List;

    public class PayrollComponentsSpecifications {
        public static Specification<PayrollComponents> filter(PayrollComponentsFilter filter) {
            if (filter == null || allFieldsNull(filter)) {
                return (root, query, cb) -> cb.conjunction();
            }
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (filter.getName() != null && !filter.getName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + filter.getName().toLowerCase() + "%"));
                }
                if (filter.getType() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
                }
                if (filter.getAmount() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("amount"), filter.getAmount()));
                }
                if (filter.getPercentage() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("percentage"), filter.getPercentage()));
                }
                if (filter.getRegulationId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("regulation").get("id"), filter.getRegulationId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }

        public static Specification<PayrollComponents> filterWithRange(PayrollComponentsFilterWithRange filterWithRange) {
            if (filterWithRange == null || allFieldsNull(filterWithRange)) {
                return (root, query, cb) -> cb.conjunction();
            }
            return (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (filterWithRange.getMinAmount() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filterWithRange.getMinAmount()));
                }
                if (filterWithRange.getMaxAmount() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filterWithRange.getMaxAmount()));
                }
                if (filterWithRange.getMinPercentage() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("percentage"), filterWithRange.getMinPercentage()));
                }
                if (filterWithRange.getMaxPercentage() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("percentage"), filterWithRange.getMaxPercentage()));
                }
                if (filterWithRange.getType() != null) {
                    predicates.add(cb.equal(root.get("type"), filterWithRange.getType()));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            };
        }

        private static boolean allFieldsNull(PayrollComponentsFilter filter) {
            return filter.getName() == null &&
                    filter.getType() == null &&
                    filter.getAmount() == null &&
                    filter.getPercentage() == null &&
                    filter.getRegulationId() == null;
        }

        private static boolean allFieldsNull(PayrollComponentsFilterWithRange filter) {
            return filter.getMinAmount() == null &&
                    filter.getMaxAmount() == null &&
                    filter.getMinPercentage() == null &&
                    filter.getMaxPercentage() == null &&
                    filter.getType() == null;
        }
    }