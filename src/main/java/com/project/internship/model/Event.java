package com.project.internship.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long id;
    @NotBlank
    @Size(min = 3, message = "Event name should be at least 3 characters long!")
    private String name;
    @NotNull
    private Long organizerId;
    @NotNull
    private Long departmentId;
    private String description;
    @Min(value = 1, message = "Capacity should be greater than 0!")
    private int capacity;
    @NotBlank
    private String location;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDate;
    private List<Employee> employeeList;
}
