package com.sm.petwellnessplus.services.appointment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.sm.petwellnessplus.models.Pet;
import com.sm.petwellnessplus.repositories.PetRepository;
import com.sm.petwellnessplus.requests.BookAppointmentRequest;
import com.sm.petwellnessplus.services.pet.IPetService;
import com.sm.petwellnessplus.services.user.IUserService;
import com.sm.petwellnessplus.utils.FeedBackMessage;
import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.enums.AppointmentStatus;
import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Appointment;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.repositories.AppointmentRepository;
import com.sm.petwellnessplus.requests.AppointmentUpdateRequest;
import com.sm.petwellnessplus.utils.PwHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final IUserService userService;
    private final IPetService petService;

    @Transactional
    @Override
    public Appointment createAppointment(BookAppointmentRequest appointmentRequest, Long senderId, Long recipientId) {
            try{
                User sender = userService.getUserById(senderId);
                User recipient = userService.getUserById(recipientId);

                Appointment appointment = appointmentRequest.getAppointment();
                List<Pet> pets = appointmentRequest.getPets();
                pets.forEach(pet -> pet.setAppointment(appointment));
                List<Pet> savedPets = petService.savePetsForAppointment(pets);
                appointment.setPets(savedPets);

                appointment.addPatient(sender);
                appointment.addVeterinarian(recipient);
                appointment.setAppointmentNo(PwHelper.generateAppointmentNo());
                appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);
                return appointmentRepository.save(appointment);
            }catch (ResourceNotFoundException rnfex){
                throw new ResourceNotFoundException(FeedBackMessage.SENDER_RECIPIENT_NOT_FOUND);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, AppointmentUpdateRequest request) {

        Appointment existAppointment = getAppointmentById(appointmentId);

        if (!Objects.equals(AppointmentStatus.WAITING_FOR_APPROVAL, existAppointment.getStatus())) {
            throw new IllegalStateException(FeedBackMessage.ALREADY_APPROVED);
        }

        existAppointment.setAppointmentDate(request.getAppointmentDate());
        existAppointment.setAppointmentTime(request.getAppointmentTime());
        existAppointment.setReason(request.getReason());

        return appointmentRepository.save(existAppointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.findById(appointmentId)
                .ifPresentOrElse(appointmentRepository::delete, () -> {
                    throw new ResourceNotFoundException(FeedBackMessage.APPOINTMENT_NOT_FOUND);
                });
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.APPOINTMENT_NOT_FOUND));
    }

    @Override
    public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmentRepository.findByAppointmentNo(appointmentNo)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.APPOINTMENT_NOT_FOUND));
    }

}
