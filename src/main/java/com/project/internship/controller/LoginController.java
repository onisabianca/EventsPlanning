package com.project.internship.controller;

import com.project.internship.model.Credentials;
import com.project.internship.model.Employee;
import com.project.internship.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(description = "Login API", name = "Login")
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    private final EmployeeService employeeService;

    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Login with credentials", description = "Returns employee data if the credentials are valid, or throws exception otherwise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Credentials are not valid")})
    @PostMapping
    public Employee login(@Valid @RequestBody Credentials credentials) {
        String username = credentials.getUsername().substring(0, 3) + "***";
        log.info("Logging in user {}", username);
        return employeeService.authenticateAndCreate(credentials);
    }
}
