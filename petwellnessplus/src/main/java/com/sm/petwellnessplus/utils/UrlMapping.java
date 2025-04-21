package com.sm.petwellnessplus.utils;

public class UrlMapping {
    /*==================================Start Common APIs========================================*/
    public static final String API = "/api/v1";
    /*==================================End Common APIs========================================*/

    /*==================================Start User APIs========================================*/
    public static final String USER = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String GET_USER_BY_ID = "/id/{userId}";
    public static final String DELETE_USER_BY_ID = "/delete/{userId}";
    public static final String GET_ALL_USERS = "/all-users";
    /*==================================End User APIs========================================*/

    /*==================================Start Appointment APIs========================================*/
    public static final String APPOINTMENT = API + "/appointments";
    public static final String CREATE_APPOINTMENT = "/book-appointment";
    public static final String UPDATE_APPOINMENT = "/update/{appointmentId}";
    public static final String GET_APPOINTMENT_BY_ID = "/id/{appointmentId}";
    public static final String DELETE_APPOINTMENT_BY_ID = "/delete/{appointmentId}";
    public static final String GET_ALL_APPOINTMENTS = "/all-appointments";
    public static final String GET_APPOINTMENT_BY_NO = "/no/{appointmentNo}";
    /*==================================End Appointment APIs========================================*/

}
