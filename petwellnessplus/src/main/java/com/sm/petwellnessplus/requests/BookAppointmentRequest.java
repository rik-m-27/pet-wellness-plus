package com.sm.petwellnessplus.requests;

import com.sm.petwellnessplus.models.Appointment;
import com.sm.petwellnessplus.models.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookAppointmentRequest {
    private Appointment appointment;
    private List<Pet> pets;
}
