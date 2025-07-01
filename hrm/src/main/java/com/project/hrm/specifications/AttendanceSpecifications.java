package com.project.hrm.specifications;

import com.project.hrm.dto.attendanceDTO.AttendanceFilter;
import com.project.hrm.dto.attendanceDTO.AttendanceFilterWithRange;
import com.project.hrm.entities.Attendance;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AttendanceSpecifications {

    // Helper methods for common conditions
    private static Specification<Attendance> hasEmployeeId(Integer employeeId) {
        return employeeId == null ? null : (root, query, cb) -> cb.equal(root.get("employee").get("id"), employeeId);
    }

    private static Specification<Attendance> hasAttendanceDate(LocalDateTime attendanceDate) {
        return attendanceDate == null ? null : (root, query, cb) -> cb.equal(root.get("attendanceDate"), attendanceDate);
    }

    private static Specification<Attendance> hasCheckIn(LocalDateTime checkIn) {
        return checkIn == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("checkIn"), checkIn);
    }

    private static Specification<Attendance> hasCheckOut(LocalDateTime checkOut) {
        return checkOut == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("checkOut"), checkOut);
    }

    private static Specification<Attendance> hasRegularTime(Float regularTime) {
        return regularTime == null ? null : (root, query, cb) -> cb.equal(root.get("regularTime"), regularTime);
    }

    private static Specification<Attendance> hasOtherTime(Float otherTime) {
        return otherTime == null ? null : (root, query, cb) -> cb.equal(root.get("otherTime"), otherTime);
    }

    // Helper methods for range conditions
    private static Specification<Attendance> attendanceDateFrom(LocalDateTime from) {
        return from == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("attendanceDate"), from);
    }

    private static Specification<Attendance> attendanceDateTo(LocalDateTime to) {
        return to == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("attendanceDate"), to);
    }

    private static Specification<Attendance> checkInFrom(LocalDateTime from) {
        return from == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("checkIn"), from);
    }

    private static Specification<Attendance> checkInTo(LocalDateTime to) {
        return to == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("checkIn"), to);
    }

    private static Specification<Attendance> checkOutFrom(LocalDateTime from) {
        return from == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("checkOut"), from);
    }

    private static Specification<Attendance> checkOutTo(LocalDateTime to) {
        return to == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("checkOut"), to);
    }

    private static Specification<Attendance> regularTimeFrom(Float from) {
        return from == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("regularTime"), from);
    }

    private static Specification<Attendance> regularTimeTo(Float to) {
        return to == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("regularTime"), to);
    }

    private static Specification<Attendance> otherTimeFrom(Float from) {
        return from == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("otherTime"), from);
    }

    private static Specification<Attendance> otherTimeTo(Float to) {
        return to == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("otherTime"), to);
    }

    public static Specification<Attendance> filter(AttendanceFilter filter) {
        if (filter == null || allFieldsNull(filter)) {
            return (root, query, cb) -> cb.conjunction();
        }

        return Specification.where(hasEmployeeId(filter.getEmployeeId()))
                .and(hasAttendanceDate(filter.getAttendanceDate()))
                .and(hasCheckIn(filter.getCheckIn()))
                .and(hasCheckOut(filter.getCheckOut()))
                .and(hasRegularTime(filter.getRegularTime()))
                .and(hasOtherTime(filter.getOtherTime()));
    }

    public static Specification<Attendance> filterWithRange(AttendanceFilterWithRange filterWithRange) {
        if (filterWithRange == null || allFieldsNull(filterWithRange)) {
            return (root, query, cb) -> cb.conjunction();
        }

        return Specification.where(hasEmployeeId(filterWithRange.getEmployeeId()))
                .and(attendanceDateFrom(filterWithRange.getAttendanceDateFrom()))
                .and(attendanceDateTo(filterWithRange.getAttendanceDateTo()))
                .and(filterWithRange.getCheckInFrom() != null ? (r, q, c) -> c.greaterThanOrEqualTo(r.get("checkIn"), filterWithRange.getCheckInFrom()) : null)
                .and(filterWithRange.getCheckInTo() != null ? (r, q, c) -> c.lessThanOrEqualTo(r.get("checkIn"), filterWithRange.getCheckInTo()) : null)
                .and(filterWithRange.getCheckOutFrom() != null ? (r, q, c) -> c.greaterThanOrEqualTo(r.get("checkOut"), filterWithRange.getCheckOutFrom()) : null)
                .and(filterWithRange.getCheckOutTo() != null ? (r, q, c) -> c.lessThanOrEqualTo(r.get("checkOut"), filterWithRange.getCheckOutTo()) : null)
                .and(filterWithRange.getRegularTimeFrom() != null ? (r, q, c) -> c.greaterThanOrEqualTo(r.get("regularTime"), filterWithRange.getRegularTimeFrom()) : null)
                .and(filterWithRange.getRegularTimeTo() != null ? (r, q, c) -> c.lessThanOrEqualTo(r.get("regularTime"), filterWithRange.getRegularTimeTo()) : null)
                .and(filterWithRange.getOtherTimeFrom() != null ? (r, q, c) -> c.greaterThanOrEqualTo(r.get("otherTime"), filterWithRange.getOtherTimeFrom()) : null)
                .and(filterWithRange.getOtherTimeTo() != null ? (r, q, c) -> c.lessThanOrEqualTo(r.get("otherTime"), filterWithRange.getOtherTimeTo()) : null);
    }

    private static boolean allFieldsNull(AttendanceFilter filter) {
        return filter.getEmployeeId() == null &&
                filter.getAttendanceDate() == null &&
                filter.getCheckIn() == null &&
                filter.getCheckOut() == null &&
                filter.getRegularTime() == null &&
                filter.getOtherTime() == null;
    }

    private static boolean allFieldsNull(AttendanceFilterWithRange filter) {
        return filter.getEmployeeId() == null &&
                filter.getAttendanceDateFrom() == null &&
                filter.getAttendanceDateTo() == null &&
                filter.getCheckInFrom() == null &&
                filter.getCheckInTo() == null &&
                filter.getCheckOutFrom() == null &&
                filter.getCheckOutTo() == null &&
                filter.getRegularTimeFrom() == null &&
                filter.getRegularTimeTo() == null &&
                filter.getOtherTimeFrom() == null &&
                filter.getOtherTimeTo() == null;
    }
}