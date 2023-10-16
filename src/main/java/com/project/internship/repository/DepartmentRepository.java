package com.project.internship.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("sql-query.properties")
public class DepartmentRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${department.random}")
    private String randomDepartmentIdSql;

    public DepartmentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long getRandomDepartmentId() {
        return namedParameterJdbcTemplate.queryForObject(randomDepartmentIdSql, new MapSqlParameterSource(), long.class);
    }
}
