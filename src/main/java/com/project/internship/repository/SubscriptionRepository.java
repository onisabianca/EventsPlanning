package com.project.internship.repository;

import com.project.internship.model.Subscription;
import com.project.internship.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("sql-query.properties")
public class SubscriptionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${subscription.add}")
    private String addSql;

    @Value("${subscription.count.forEventId}")
    private String countSubscribersForEventSql;

    @Value("${subscription.delete.byEventId}")
    private String deleteByEventIdSql;

    public SubscriptionRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void add(Subscription subscription) {
        SqlParameterSource parameterSource = addParametersSubscription(subscription);
        namedParameterJdbcTemplate.update(addSql, parameterSource);
    }

    public int findNumberOfSubscribersForEventId(Long eventId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(Utils.EVENT_ID, eventId);
        return namedParameterJdbcTemplate.queryForObject(countSubscribersForEventSql, parameterSource, int.class);
    }

    public void deleteByEventId(Long eventId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(Utils.EVENT_ID, eventId);
        namedParameterJdbcTemplate.update(deleteByEventIdSql, parameterSource);
    }

    private SqlParameterSource addParametersSubscription(Subscription subscription) {
        return new MapSqlParameterSource().addValue(Utils.EMPLOYEE_ID, subscription.getEmployeeId())
                .addValue(Utils.EVENT_ID, subscription.getEventId());
    }
}
