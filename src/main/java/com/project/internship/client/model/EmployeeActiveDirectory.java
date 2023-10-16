package com.project.internship.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeActiveDirectory {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String title;
}
