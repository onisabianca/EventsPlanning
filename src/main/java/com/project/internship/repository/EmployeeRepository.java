package com.project.internship.repository;

import com.project.internship.mapper.EmployeeDBMapper;
import com.project.internship.model.Employee;
import com.project.internship.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("sql-query.properties")
public class EmployeeRepository {
    private final NamedParameterJdbcTemplate template;

    @Value("${employee.add}")
    private String queryAdd;

    @Value("${employee.find.byId}")
    private String queryFindById;

    @Value("${employee.find.byUsername}")
    private String queryFindByUsername;

    public EmployeeRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Long add(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = addParametersEmployee(employee);
        template.update(queryAdd, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Employee getById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(Utils.ID, id);
        return template.queryForObject(queryFindById, parameterSource, new EmployeeDBMapper());
    }

    public Employee getByUsername(String username) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(Utils.USERNAME, username);
        return template.queryForObject(queryFindByUsername, parameterSource, new EmployeeDBMapper());
    }

    private SqlParameterSource addParametersEmployee(Employee employee) {
        return new MapSqlParameterSource().addValue(Utils.ID, employee.getId())
                .addValue(Utils.FIRST_NAME, employee.getFirstName())
                .addValue(Utils.LAST_NAME, employee.getLastName())
                .addValue(Utils.EMAIL, employee.getEmail())
                .addValue(Utils.USERNAME, employee.getUsername())
                .addValue(Utils.TITLE, employee.getTitle())
                .addValue(Utils.DEPARTMENT_ID, employee.getDepartmentId());
    }
}
