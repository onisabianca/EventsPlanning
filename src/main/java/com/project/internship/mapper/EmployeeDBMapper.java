package com.project.internship.mapper;

import com.project.internship.model.Employee;
import com.project.internship.util.Utils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDBMapper implements RowMapper<Employee> {
    public Employee mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong(Utils.ID));
        employee.setFirstName(rs.getString(Utils.FIRST_NAME));
        employee.setLastName(rs.getString(Utils.LAST_NAME));
        employee.setEmail(rs.getString(Utils.EMAIL));
        employee.setUsername(rs.getString(Utils.USERNAME));
        employee.setTitle(rs.getString(Utils.TITLE));
        employee.setDepartmentId(rs.getLong(Utils.DEPARTMENT_ID));

        return employee;
    }
}
