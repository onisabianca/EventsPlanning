package com.project.internship.mapper;

import com.project.internship.model.Subscription;
import com.project.internship.util.Utils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SubscriptionMapper implements RowMapper<Subscription> {
    @Override
    public Subscription mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setEventId(resultSet.getLong(Utils.EVENT_ID));
        subscription.setEmployeeId(resultSet.getLong(Utils.EMPLOYEE_ID));
        subscription.setJoinDate(resultSet.getObject(Utils.JOIN_DATE, LocalDateTime.class));

        return subscription;
    }
}
