package com.project.internship.controller;

import com.project.internship.model.Employee;
import com.project.internship.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(description = "Employee APIs to access employee data", name = "Employee Resources")
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get employee by ID", description = "Returns the employee with the given ID if it exists, otherwise throws a runtime exception.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    @GetMapping("/{id}")
    public Employee getById(@PathVariable(name = "id") Long id) {
        log.info("Searching for employee with ID {}", id);
        return employeeService.getEmployeeByID(id);
    }

    @Operation(summary = "Create employee", description = "Create a employee if the fields are correct given id does not exist, otherwise throws a runtime exception.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created"),
            @ApiResponse(responseCode = "404", description = "Employee could not be created"),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long add(@Valid @RequestBody Employee employee) {
        Long id = employeeService.addEmployee(employee);
        log.info("Trying to create employee with ID {}", id);
        return id;
    }
}
