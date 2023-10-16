package com.project.internship.mapper;

import com.project.internship.model.Event;
import com.project.internship.util.Utils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EventMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong(Utils.ID));
        event.setName(resultSet.getString(Utils.NAME));
        event.setOrganizerId(resultSet.getLong(Utils.ORGANIZER_ID));
        event.setDepartmentId(resultSet.getLong(Utils.DEPARTMENT_ID));
        event.setDescription(resultSet.getString(Utils.DESCRIPTION));
        event.setCapacity(resultSet.getInt(Utils.CAPACITY));
        event.setLocation(resultSet.getString(Utils.LOCATION));
        event.setStartDate(resultSet.getObject(Utils.START_DATE, LocalDateTime.class));
        event.setEndDate(resultSet.getObject(Utils.END_DATE, LocalDateTime.class));

        return event;
    }
}
