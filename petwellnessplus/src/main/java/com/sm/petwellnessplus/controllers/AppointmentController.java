package com.sm.petwellnessplus.controllers;

import java.util.List;

import com.sm.petwellnessplus.requests.BookAppointmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Appointment;
import com.sm.petwellnessplus.requests.AppointmentUpdateRequest;
import com.sm.petwellnessplus.response.ApiResponse;
import com.sm.petwellnessplus.services.appointment.AppointmentService;
import com.sm.petwellnessplus.utils.FeedBackMessage;
import com.sm.petwellnessplus.utils.UrlMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.APPOINTMENT)
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping(UrlMapping.GET_ALL_APPOINTMENTS)
    public ResponseEntity<ApiResponse> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse(FeedBackMessage.APPOINTMENTS_FOUND, appointments));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.CREATE_APPOINTMENT)
    public ResponseEntity<ApiResponse> bookAppointentment(@RequestBody BookAppointmentRequest appointmentRequest,
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        try {
            Appointment theAppointment = appointmentService.createAppointment(appointmentRequest, senderId, recipientId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(FeedBackMessage.APPOINTMENT_CREATE_SUCCESS, theAppointment));
        } catch (ResourceNotFoundException rnfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(rnfex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_ID)
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long appointmentId) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse(FeedBackMessage.APPOINTMENT_FOUND, appointment));
        } catch (ResourceNotFoundException rnfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(rnfex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_NO)
    public ResponseEntity<ApiResponse> getAppointmentByNo(@PathVariable String appointmentNo) {
        try {
            Appointment appointment = appointmentService.getAppointmentByNo(appointmentNo);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse(FeedBackMessage.APPOINTMENT_FOUND, appointment));
        } catch (ResourceNotFoundException rnfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(rnfex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_APPOINTMENT_BY_ID)
    public ResponseEntity<ApiResponse> deleteAppointmentById(@PathVariable Long appointmentId) {
        try {
            appointmentService.deleteAppointment(appointmentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(FeedBackMessage.APPOINTMENT_DELETE_SUCCESS, null));
        } catch (ResourceNotFoundException rnfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(rnfex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_APPOINTMENT)
    public ResponseEntity<ApiResponse> updateAppointmentById(@PathVariable Long appointmentId,
            @RequestBody AppointmentUpdateRequest request) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(FeedBackMessage.APPOINTMENT_UPDATE_SUCCESS, updatedAppointment));
        } catch (ResourceNotFoundException rnfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(rnfex.getMessage(), null));
        } catch (IllegalStateException ise) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ApiResponse(ise.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

}
