package com.sm.petwellnessplus.requests;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
}
