package com.project.internship.service;

import com.project.internship.client.ActiveDirectoryClient;
import com.project.internship.exception.DuplicateKeyConstraintException;
import com.project.internship.exception.NotFoundException;
import com.project.internship.model.Credentials;
import com.project.internship.model.Employee;
import com.project.internship.repository.DepartmentRepository;
import com.project.internship.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ActiveDirectoryClient activeDirectoryClient;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ActiveDirectoryClient activeDirectoryClient) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.activeDirectoryClient = activeDirectoryClient;
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeByID(long id) {
        try {
            return employeeRepository.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Employee with ID " + id + " not found!");
        }
    }

    public long addEmployee(Employee employee) {
        try {
            return employeeRepository.add(employee);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyConstraintException(e.getMessage());
        }
    }

    public Employee authenticateAndCreate(Credentials credentials) {
        Employee activeDirectoryEmployee = activeDirectoryClient.authenticateUser(credentials);

        try {
            return employeeRepository.getByUsername(credentials.getUsername());
        } catch (EmptyResultDataAccessException e) {
            log.info("User doesn't exist in local DB, creating user!");
        }
        activeDirectoryEmployee.setDepartmentId(departmentRepository.getRandomDepartmentId());
        employeeRepository.add(activeDirectoryEmployee);

        return employeeRepository.getByUsername(credentials.getUsername());
    }
}
