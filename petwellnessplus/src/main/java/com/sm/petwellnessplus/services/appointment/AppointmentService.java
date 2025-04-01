package com.sm.petwellnessplus.services.appointment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.enums.AppointmentStatus;
import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Appointment;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.repositories.AppointmentRepository;
import com.sm.petwellnessplus.repositories.UserRepository;
import com.sm.petwellnessplus.requests.AppointmentUpdateRequest;
import com.sm.petwellnessplus.utils.PwHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    public Appointment createAppointment(Appointment appointment, Long senderId, Long recipientId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> recipient = userRepository.findById(recipientId);

        if (sender.isPresent() && recipient.isPresent()) {
            appointment.addPatient(sender.get());
            appointment.addVeterinarian(recipient.get());
            appointment.setAppointmentNo(PwHelper.generateAppointmentNo());
            appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);
            return appointmentRepository.save(appointment);
        }
        throw new ResourceNotFoundException("Sender or recipient not found");
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, AppointmentUpdateRequest request) {

        Appointment exisAppointment = getAppointmentById(appointmentId);

        if (!Objects.equals(AppointmentStatus.WAITING_FOR_APPROVAL, exisAppointment.getStatus())) {
            throw new IllegalStateException("Sorry, this appointment can no longer be updated.");
        }

        exisAppointment.setAppointmentDate(request.getAppointmentDate());
        exisAppointment.setAppointmentTime(request.getAppointmentTime());
        exisAppointment.setReason(request.getReason());

        return appointmentRepository.save(exisAppointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.findById(appointmentId)
                .ifPresentOrElse(appointment -> appointmentRepository.delete(appointment), () -> {
                    throw new ResourceNotFoundException("Appointment not found");
                });
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    @Override
    public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmentRepository.findByAppointmentNo(appointmentNo)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

}
