package com.project.internship.extractor;

import com.project.internship.model.Employee;
import com.project.internship.model.Event;
import com.project.internship.util.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventWithEmployeesExtractor implements ResultSetExtractor<Event> {
    @Override
    public Event extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Event event = null;
        List<Employee> employees = new ArrayList<>();

        while ((resultSet.next())) {
            if (event == null) {
                event = setEvent(resultSet);
            }
            setEmployee(employees, resultSet);
        }

        if (event != null) {
            event.setEmployeeList(employees);
        }

        return event;
    }

    private Event setEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong(Utils.EVENT_ID));
        event.setName(resultSet.getString(Utils.NAME));
        event.setOrganizerId(resultSet.getLong(Utils.ORGANIZER_ID));
        event.setDepartmentId(resultSet.getLong(Utils.EVENT_DEPARTMENT_ID));
        event.setDescription(resultSet.getString(Utils.DESCRIPTION));
        event.setCapacity(resultSet.getInt(Utils.CAPACITY));
        event.setLocation(resultSet.getString(Utils.LOCATION));
        event.setStartDate(resultSet.getObject(Utils.START_DATE, LocalDateTime.class));
        event.setEndDate(resultSet.getObject(Utils.END_DATE, LocalDateTime.class));

        return event;
    }

    private void setEmployee(List<Employee> employees, ResultSet resultSet) throws SQLException {
        if (resultSet.getLong(Utils.EMPLOYEE_ID) != 0) {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong(Utils.EMPLOYEE_ID));
            employee.setFirstName(resultSet.getString(Utils.FIRST_NAME));
            employee.setLastName(resultSet.getString(Utils.LAST_NAME));
            employee.setEmail(resultSet.getString(Utils.EMAIL));
            employee.setUsername(resultSet.getString(Utils.USERNAME));
            employee.setTitle(resultSet.getString(Utils.TITLE));
            employee.setDepartmentId(resultSet.getLong(Utils.EMPLOYEE_DEPARTMENT_ID));
            employees.add(employee);
        }
    }
}
