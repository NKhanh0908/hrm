package com.project.hrm.employee.specification;

import com.project.hrm.employee.dto.dayOffDTO.DayOffFilter;
import com.project.hrm.employee.dto.dayOffDTO.DayOffFilterDynamic;
import com.project.hrm.employee.entity.DayOff;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DayOffSpecifications {
    private static Specification<DayOff> hasRequestDay(LocalDateTime requestDay) {
        return requestDay == null ? null : (root, query, cb) -> cb.equal(root.get("requestDay"), requestDay);
    }

    private static Specification<DayOff> hasStartDate(LocalDateTime startDate) {
        return startDate == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    private static Specification<DayOff> hasEndDate(LocalDateTime endDate) {
        return endDate == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    private static Specification<DayOff> hasReason(String reason) {
        return reason == null || reason.isEmpty() ? null : (root, query, cb) -> cb.like(cb.lower(root.get("reason")), "%" + reason.toLowerCase() + "%");
    }

    private static Specification<DayOff> hasStatus(String status) {
        return status == null || status.isEmpty() ? null : (root, query, cb) -> cb.equal(cb.lower(root.get("status")), status.toLowerCase());
    }

    private static Specification<DayOff> hasRequestDayRange(LocalDateTime from, LocalDateTime to) {
        return from == null || to == null ? null : (root, query, cb) -> cb.between(root.get("requestDay"), from, to);
    }

    private static Specification<DayOff> hasStartEndOverlap(LocalDateTime startDateFrom, LocalDateTime endDateTo) {
        return startDateFrom == null || endDateTo == null ? null : (root, query, cb) -> cb.and(
                cb.lessThanOrEqualTo(root.get("startDate"), endDateTo),
                cb.greaterThanOrEqualTo(root.get("endDate"), startDateFrom)
        );
    }

    public static Specification<DayOff> filter(DayOffFilter filter) {
        if (filter == null || allFieldsNull(filter)) {
            return (root, query, cb) -> cb.conjunction();
        }
        return Specification.where(hasRequestDay(filter.getRequestDay()))
                .and(hasStartDate(filter.getStartDate()))
                .and(hasEndDate(filter.getEndDate()))
                .and(hasReason(filter.getReason()))
                .and(hasStatus(filter.getStatus()));
    }

    public static Specification<DayOff> filterDynamic(DayOffFilterDynamic filterDynamic) {
        if (filterDynamic == null || allFieldsNull(filterDynamic)) {
            return (root, query, cb) -> cb.conjunction();
        }
        Specification<DayOff> spec = Specification.where(null);
        if (filterDynamic.getUseRequestDayFilter() != null && filterDynamic.getUseRequestDayFilter()) {
            spec = spec.and(hasRequestDayRange(filterDynamic.getRequestDayFrom(), filterDynamic.getRequestDayTo()));
        }
        if (filterDynamic.getUseStartEndOverlapFilter() != null && filterDynamic.getUseStartEndOverlapFilter()) {
            spec = spec.and(hasStartEndOverlap(filterDynamic.getStartDateFrom(), filterDynamic.getEndDateTo()));
        }
        if (filterDynamic.getStatus() != null && !filterDynamic.getStatus().trim().isEmpty()) {
            spec = spec.and(hasStatus(filterDynamic.getStatus()));
        }
        return spec;
    }

    private static boolean allFieldsNull(DayOffFilter filter) {
        return filter.getRequestDay() == null &&
                filter.getStartDate() == null &&
                filter.getEndDate() == null &&
                filter.getReason() == null &&
                filter.getStatus() == null;
    }

    private static boolean allFieldsNull(DayOffFilterDynamic filter) {
        return filter.getRequestDayFrom() == null &&
                filter.getRequestDayTo() == null &&
                filter.getStartDateFrom() == null &&
                filter.getEndDateTo() == null &&
                filter.getStatus() == null;
    }
}
