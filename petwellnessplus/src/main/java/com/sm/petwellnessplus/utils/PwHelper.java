package com.sm.petwellnessplus.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PwHelper {

    private static final String APPOINTMENT_NO_PREFIX = "APPT-";
    private static final String APPOINTMENT_NO_SUFFIX = "-";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static LocalDate date = null;
    private static String dateStr = null;
    private static Long waiting = null;

    public static String generateAppointmentNo() {
        StringBuilder appointmentNo = new StringBuilder();
        appointmentNo.append(APPOINTMENT_NO_PREFIX);
        appointmentNo.append(getTodaysDate());
        appointmentNo.append(APPOINTMENT_NO_SUFFIX);
        appointmentNo.append(waiting++);
        return appointmentNo.toString();
    }

    private static String getTodaysDate() {
        if (date == null || date.isBefore(LocalDate.now())) {
            date = LocalDate.now();
            dateStr = date.format(dateTimeFormatter);
            waiting = 1L;
        }
        return dateStr;
    }

}
