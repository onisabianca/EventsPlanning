package com.project.internship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    @NotBlank
    @Size(min = 3, message = "First name should be at least 3 characters long!")
    private String firstName;
    @NotBlank
    @Size(min = 3, message = "Last name should be at least 3 characters long!")
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    private String title;
    @NotNull
    private Long departmentId;

    public Employee(String firstName, String lastName, String email, String username, String title) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setUsername(username);
        this.setTitle(title);
    }
}
