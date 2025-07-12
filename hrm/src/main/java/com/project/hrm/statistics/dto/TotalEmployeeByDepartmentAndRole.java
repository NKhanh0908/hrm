package com.project.hrm.statistics.dto;

public interface TotalEmployeeByDepartmentAndRole {
    Integer getRoleId();
    String getRoleName();
    Integer getDepartmentId();
    String getDepartmentName();
    Integer total();
}
