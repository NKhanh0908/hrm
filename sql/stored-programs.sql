use hrm;

CREATE PROCEDURE getInfoApply(
    IN id_employee INT
)
BEGIN
        SELECT cp.name, cp.email, rl.name as position_apply
        FROM (
                 SELECT candidate_profile_id, recruitment_id
                 FROM apply
                 WHERE id = id_employee
             ) a
                 INNER JOIN candidate_profile cp
                            ON a.candidate_profile_id = cp.id
                 INNER JOIN recruitment rm
                            ON a.recruitment_id = rm.id
                 INNER JOIN recruitment_requirements rr ON rm.recruitment_requirements_id = rr.id
                 INNER JOIN role rl ON rr.role_id = rl.id;
END;

CREATE PROCEDURE get_role_id_by_apply_id(
    IN apply_id INT,
    OUT role_id INT
)
    BEGIN
        SELECT rl.id INTO role_id
        FROM (
                 SELECT recruitment_id
                 FROM apply
                 WHERE id = apply_id
             ) a
                 INNER JOIN recruitment r ON a.recruitment_id = r.id
                 INNER JOIN recruitment_requirements rr ON r.recruitment_requirements_id = rr.id
                 INNER JOIN role rl ON rr.role_id = rl.id;
    END;

create procedure exists_overlapping_active_contract(
    in employee_id int,
    in role_id int,
    in new_start datetime,
    in new_end datetime
)
    begin
        select IF(count(c.id) > 0, true, false)
        from Contracts c
        where c.employee_id = employee_id
          and c.role_id = role_id
          and c.contract_status in ('SIGNED', 'ACTIVE', 'SUSPENDED')
          and c.start_date <= coalesce(new_start, c.end_date)
          and (c.end_date is null or c.end_date >= new_end);
    end;

create procedure get_total_contract_by_status_and_salary(
    in contract_status varchar(20)
)
begin
    SELECT
        CASE
            WHEN c.base_salary < 10000000 THEN 'Less than 10m'
            WHEN c.base_salary BETWEEN 10000000 AND 20000000 THEN 'to 10-20m'
            WHEN c.base_salary BETWEEN 20000001 AND 30000000 THEN 'to 20-30m'
            ELSE 'More than 30m'
            END AS salary_range,
        COUNT(*) AS contract_count
    FROM contracts c
    WHERE c.contract_status = contract_status
    GROUP BY salary_range
    ORDER BY MIN(c.base_salary);
end;

SELECT d.*
FROM departments d
         JOIN role r ON r.departments_id = d.id
         JOIN (
    SELECT c.role_id
    FROM contracts c
    WHERE c.employee_id = :employeeId
      and c.contract_status in ('ACTIVE', 'SIGNED')
    order by
        IF(c.contract_status = 'ACTIVE', 1, 2),
        c.start_date desc
    limit 1
) latest_contract ON latest_contract.role_id = r.id;


create procedure get_departments_by_employee_id(
    in employee_id int
)
begin
    SELECT d.*
    FROM departments d
             JOIN role r ON r.departments_id = d.id
             JOIN (
        SELECT c.role_id
        FROM contracts c
        WHERE c.employee_id = employee_id
          and c.contract_status in ('ACTIVE', 'SIGNED')
        order by
            IF(c.contract_status = 'ACTIVE', 1, 2),
            c.start_date desc
        limit 1
    ) latest_contract ON latest_contract.role_id = r.id;
end;

CREATE PROCEDURE get_total_employee_by_department()
BEGIN
    SELECT d.id, d.department_name, COUNT(*) AS total
    FROM employees e
             INNER JOIN contracts c ON e.id = c.employee_id
             INNER JOIN role r ON c.role_id = r.id
             INNER JOIN departments d ON r.departments_id = d.id
    GROUP BY d.id;
END;

CREATE PROCEDURE get_total_employee_by_role()
BEGIN
    SELECT r.id, r.name, COUNT(*) AS total
    FROM employees e
             INNER JOIN contracts c ON e.id = c.employee_id
             INNER JOIN role r ON c.role_id = r.id
    GROUP BY r.id;
END;

CREATE PROCEDURE get_total_employee_by_department_and_role()
BEGIN
    SELECT r.id AS role_id, r.name AS role_name, d.id AS department_id, d.department_name, COUNT(*) AS total
    FROM employees e
             INNER JOIN contracts c ON e.id = c.employee_id
             INNER JOIN role r ON c.role_id = r.id
             INNER JOIN departments d ON r.departments_id = d.id
    GROUP BY r.id, d.id, d.department_name;
END;

CREATE PROCEDURE get_employees_by_department_id(IN dep_id INT, IN limit_size INT, IN offset_val INT)
BEGIN
    SELECT e.*
    FROM contracts c
             INNER JOIN employees e ON c.employee_id = e.id
             INNER JOIN role r ON c.role_id = r.id
    WHERE r.departments_id = dep_id
    LIMIT limit_size OFFSET offset_val;
END;
