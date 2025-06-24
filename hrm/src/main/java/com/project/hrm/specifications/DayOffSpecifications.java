package com.project.hrm.specifications;

import com.project.hrm.dto.dayOffDTO.DayOffFilter;
import com.project.hrm.dto.dayOffDTO.DayOffFilterDynamic;
import com.project.hrm.entities.DayOff;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DayOffSpecifications {
    public static Specification<DayOff> filter(DayOffFilter filter) {
        return (root, query, cb) -> {
            Specification<DayOff> spec = Specification.where(null);

            if (filter.getRequestDay() != null) {
                spec = spec.and((root1, query1, cb1) ->
                        cb1.equal(root1.get("requestDay"), filter.getRequestDay()));
            }

            if (filter.getStartDate() != null) {
                spec = spec.and((root1, query1, cb1) ->
                        cb1.greaterThanOrEqualTo(root1.get("startDate"), filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                spec = spec.and((root1, query1, cb1) ->
                        cb1.lessThanOrEqualTo(root1.get("endDate"), filter.getEndDate()));
            }

            if (filter.getReason() != null && !filter.getReason().isEmpty()) {
                spec = spec.and((root1, query1, cb1) ->
                        cb1.like(cb1.lower(root1.get("reason")), "%" + filter.getReason().toLowerCase() + "%"));
            }

            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                spec = spec.and((root1, query1, cb1) ->
                        cb1.equal(cb1.lower(root1.get("status")), filter.getStatus().toLowerCase()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

    public static Specification<DayOff> filterDynamic(DayOffFilterDynamic filterDynamic) {
        return (root, query, cb) -> {
            Specification<DayOff> spec = Specification.where(null);

            if (filterDynamic.getUseRequestDayFilter() != null && filterDynamic.getUseRequestDayFilter()) {
                if (filterDynamic.getRequestDayFrom() != null && filterDynamic.getRequestDayTo() != null) {
                    spec = spec.and((r, q, c) -> c.between(r.get("requestDay"), filterDynamic.getRequestDayFrom(), filterDynamic.getRequestDayTo()));
                }
            }

            if (filterDynamic.getUseStartEndOverlapFilter() != null && filterDynamic.getUseStartEndOverlapFilter()) {
                if (filterDynamic.getStartDateFrom() != null && filterDynamic.getEndDateTo() != null) {
                    // Điều kiện giao nhau: startDate <= endDateTo && endDate >= startDateFrom
                    spec = spec.and((r, q, c) -> c.and(
                            c.lessThanOrEqualTo(r.get("startDate"), filterDynamic.getEndDateTo()),
                            c.greaterThanOrEqualTo(r.get("endDate"), filterDynamic.getStartDateFrom())
                    ));
                }
            }

            if (filterDynamic.getStatus() != null && !filterDynamic.getStatus().trim().isEmpty()) {
                spec = spec.and((r, q, c) -> c.equal(c.lower(r.get("status")), filterDynamic.getStatus().toLowerCase()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

}
