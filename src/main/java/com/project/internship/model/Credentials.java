package com.project.internship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
