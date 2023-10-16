package com.project.internship.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @NotNull
    private Long employeeId;
    @NotNull
    private Long eventId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime joinDate;

    public Subscription(Long employeeId, Long eventId) {
        this.employeeId = employeeId;
        this.eventId = eventId;
    }
}
