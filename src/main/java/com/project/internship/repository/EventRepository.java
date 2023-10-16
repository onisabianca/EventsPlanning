package com.project.internship.repository;

import com.project.internship.extractor.EventWithEmployeesExtractor;
import com.project.internship.model.Event;
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
public class EventRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${event.find.byId}")
    private String findByIdSql;

    @Value("${event.add}")
    private String addSql;

    @Value("${event.update}")
    private String updateSql;

    @Value("${event.delete}")
    private String deleteSql;

    public EventRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Event getById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(Utils.ID, id);
        return namedParameterJdbcTemplate.query(findByIdSql, parameterSource, new EventWithEmployeesExtractor());
    }

    public Long add(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = addParametersEvent(event);
        namedParameterJdbcTemplate.update(addSql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int update(Event event) {
        SqlParameterSource parameterSource = addParametersEvent(event);
        return namedParameterJdbcTemplate.update(updateSql, parameterSource);
    }

    public void delete(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(Utils.ID, id);
        namedParameterJdbcTemplate.update(deleteSql, parameterSource);
    }

    private SqlParameterSource addParametersEvent(Event event) {
        return new MapSqlParameterSource().addValue(Utils.ID, event.getId())
                .addValue(Utils.NAME, event.getName())
                .addValue(Utils.ORGANIZER_ID, event.getOrganizerId())
                .addValue(Utils.DEPARTMENT_ID, event.getDepartmentId())
                .addValue(Utils.DESCRIPTION, event.getDescription())
                .addValue(Utils.CAPACITY, event.getCapacity())
                .addValue(Utils.LOCATION, event.getLocation())
                .addValue(Utils.START_DATE, event.getStartDate())
                .addValue(Utils.END_DATE, event.getEndDate());
    }
}
