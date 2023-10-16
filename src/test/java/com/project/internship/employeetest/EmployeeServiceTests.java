package com.project.internship.employeetest;

import com.project.internship.client.ActiveDirectoryClient;
import com.project.internship.exception.DuplicateKeyConstraintException;
import com.project.internship.exception.InvalidCredentialsException;
import com.project.internship.exception.NotFoundException;
import com.project.internship.model.Credentials;
import com.project.internship.model.Employee;
import com.project.internship.repository.EmployeeRepository;
import com.project.internship.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ActiveDirectoryClient activeDirectoryClient;

    @Test
    public void find_existentEmployee() {
        Employee expectedEmployee = instantiateEmployee();
        Mockito.when(employeeRepository.getById(expectedEmployee.getId())).thenReturn(expectedEmployee);

        Employee foundEmployee = employeeService.getEmployeeByID(100L);

        assertNotNull(foundEmployee);
        assertEquals(foundEmployee, expectedEmployee);
    }

    @Test
    public void find_nonExistentEmployee_throwsNotFoundException() {
        Mockito.when(employeeRepository.getById(2000L)).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NotFoundException.class, () -> {
            employeeService.getEmployeeByID(2000L);
        });
    }

    @Test
    public void add_correctEmployee() {
        Employee employee = instantiateEmployee();
        Mockito.when(employeeRepository.add(employee)).thenReturn(employee.getId());

        Long foundId = employeeService.addEmployee(employee);

        assertEquals(employee.getId(), foundId);
    }

    @Test
    public void add_existentEmployee() {
        Employee employee = instantiateEmployee();
        Mockito.when(employeeRepository.add(employee)).thenThrow(DuplicateKeyConstraintException.class);

        assertThrows(DuplicateKeyConstraintException.class, () -> {
            employeeService.addEmployee(employee);
        });
    }

    @Test
    public void login_invalidCredentials_throwsInvalidCredentialsException() {
        Credentials credentials = new Credentials("ana", "popaaa");
        Mockito.when(activeDirectoryClient.authenticateUser(credentials)).thenThrow(InvalidCredentialsException.class);

        assertThrows(InvalidCredentialsException.class, () -> {
            employeeService.authenticateAndCreate(credentials);
        });
    }

    @Test
    public void login_validCredentials() {
        Credentials credentials = new Credentials("onisabiaaa", "aaaaa");
        Employee expectedEmployee = instantiateEmployee();
        Mockito.when(activeDirectoryClient.authenticateUser(credentials)).thenReturn(expectedEmployee);
        Mockito.when(employeeRepository.getByUsername(expectedEmployee.getUsername())).thenReturn(expectedEmployee);

        Employee foundEmployee = employeeService.authenticateAndCreate(credentials);

        assertEquals(foundEmployee, expectedEmployee);
    }

    private Employee instantiateEmployee() {
        return new Employee(100L, "Bi", "Onisa", "onibia", "onisabiaaa", "intern", 1L);
    }
}
