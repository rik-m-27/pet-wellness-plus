package com.sm.petwellnessplus.services.appointment;

import java.util.List;

import com.sm.petwellnessplus.models.Appointment;
import com.sm.petwellnessplus.requests.AppointmentUpdateRequest;
import com.sm.petwellnessplus.requests.BookAppointmentRequest;

public interface IAppointmentService {
    Appointment createAppointment(BookAppointmentRequest appointmentRequest, Long sender, Long receiver);

    List<Appointment> getAllAppointments();

    Appointment updateAppointment(Long appointmentId, AppointmentUpdateRequest request);

    void deleteAppointment(Long appointmentId);

    Appointment getAppointmentById(Long appointmentId);

    Appointment getAppointmentByNo(String appointmentNo);
}
