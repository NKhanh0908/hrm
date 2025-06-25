package com.project.hrm.specifications;

import com.project.hrm.dto.attendanceDTO.AttendanceFilter;
import com.project.hrm.dto.attendanceDTO.AttendanceFilterWithRange;
import com.project.hrm.entities.Attendance;
import org.springframework.data.jpa.domain.Specification;

public class AttendanceSpecifications {

    public static Specification<Attendance> filter(AttendanceFilter filter) {
        return (root, query, cb) -> {
            Specification<Attendance> spec = Specification.where(null);

            if (filter.getEmployeeId() != null) {
                spec = spec.and((r, q, c) ->
                        c.equal(r.get("employee").get("id"), filter.getEmployeeId()));
            }

            if (filter.getAttendanceDate() != null) {
                spec = spec.and((r, q, c) ->
                        c.equal(r.get("attendanceDate"), filter.getAttendanceDate()));
            }

            if (filter.getCheckIn() != null) {
                spec = spec.and((r, q, c) ->
                        c.greaterThanOrEqualTo(r.get("checkIn"), filter.getCheckIn()));
            }

            if (filter.getCheckOut() != null) {
                spec = spec.and((r, q, c) ->
                        c.lessThanOrEqualTo(r.get("checkOut"), filter.getCheckOut()));
            }

            if (filter.getRegularTime() != null) {
                spec = spec.and((r, q, c) ->
                        c.equal(r.get("regularTime"), filter.getRegularTime()));
            }

            if (filter.getOtherTime() != null) {
                spec = spec.and((r, q, c) ->
                        c.equal(r.get("otherTime"), filter.getOtherTime()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

    public static Specification<Attendance> filterWithRange(AttendanceFilterWithRange filterWithRange) {
        return (root, query, cb) -> {
            Specification<Attendance> spec = Specification.where(null);

            if (filterWithRange.getEmployeeId() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("employee").get("id"), filterWithRange.getEmployeeId()));
            }

            // attendanceDate
            if (filterWithRange.getAttendanceDateFrom() != null) {
                spec = spec.and((r, q, c) -> c.greaterThanOrEqualTo(r.get("attendanceDate"), filterWithRange.getAttendanceDateFrom()));
            }
            if (filterWithRange.getAttendanceDateTo() != null) {
                spec = spec.and((r, q, c) -> c.lessThanOrEqualTo(r.get("attendanceDate"), filterWithRange.getAttendanceDateTo()));
            }

            // checkIn
            if (filterWithRange.getCheckInFrom() != null) {
                spec = spec.and((r, q, c) -> c.greaterThanOrEqualTo(r.get("checkIn"), filterWithRange.getCheckInFrom()));
            }
            if (filterWithRange.getCheckInTo() != null) {
                spec = spec.and((r, q, c) -> c.lessThanOrEqualTo(r.get("checkIn"), filterWithRange.getCheckInTo()));
            }

            // checkOut
            if (filterWithRange.getCheckOutFrom() != null) {
                spec = spec.and((r, q, c) -> c.greaterThanOrEqualTo(r.get("checkOut"), filterWithRange.getCheckOutFrom()));
            }
            if (filterWithRange.getCheckOutTo() != null) {
                spec = spec.and((r, q, c) -> c.lessThanOrEqualTo(r.get("checkOut"), filterWithRange.getCheckOutTo()));
            }

            // regularTime
            if (filterWithRange.getRegularTimeFrom() != null) {
                spec = spec.and((r, q, c) -> c.greaterThanOrEqualTo(r.get("regularTime"), filterWithRange.getRegularTimeFrom()));
            }
            if (filterWithRange.getRegularTimeTo() != null) {
                spec = spec.and((r, q, c) -> c.lessThanOrEqualTo(r.get("regularTime"), filterWithRange.getRegularTimeTo()));
            }

            // otherTime
            if (filterWithRange.getOtherTimeFrom() != null) {
                spec = spec.and((r, q, c) -> c.greaterThanOrEqualTo(r.get("otherTime"), filterWithRange.getOtherTimeFrom()));
            }
            if (filterWithRange.getOtherTimeTo() != null) {
                spec = spec.and((r, q, c) -> c.lessThanOrEqualTo(r.get("otherTime"), filterWithRange.getOtherTimeTo()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

}
