package com.project.hrm.dto.statisticsDTO;

public interface TotalEmployeeByDepartmentAndRole {
    Integer getRoleId();
    String getRoleName();
    Integer getDepartmentId();
    String getDepartmentName();
    Integer total();
}
