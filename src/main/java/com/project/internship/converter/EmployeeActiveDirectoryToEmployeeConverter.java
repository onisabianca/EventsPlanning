package com.project.internship.converter;

import com.project.internship.client.model.EmployeeActiveDirectory;
import com.project.internship.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeActiveDirectoryToEmployeeConverter implements Converter<EmployeeActiveDirectory, Employee> {
    @Override
    public Employee convert(EmployeeActiveDirectory source) {
        return new Employee(source.getFirstName(), source.getLastName(), source.getEmail(),
                source.getUsername(), source.getTitle());
    }
}
