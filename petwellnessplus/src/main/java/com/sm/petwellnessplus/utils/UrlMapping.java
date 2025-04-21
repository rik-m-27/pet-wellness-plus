package com.sm.petwellnessplus.utils;

public class UrlMapping {
    public static final String API = "/api/v1";
    public static final String USER = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String GET_USER_BY_ID = "/{userId}";
    public static final String DELETE_USER_BY_ID = "/delete/{userId}";
    public static final String GET_ALL_USERS = "/all-users";
    public static final String APPOINTMENT = API + "/appointments";
    public static final String ALL = "/all";
    public static final String CREATE_APPOINTMENT = "/create-appointment";
    public static final String UPDATE_APPOINMENT = "/update/{appointmentId}";
    public static final String GET_APPOINTMENT_BY_ID = "/{appointmentId}";
    public static final String DELETE_APPOINTMENT_BY_ID = "/delete/{appointmentId}";
    public static final String GET_ALL_APPOINTMENTS = "/all-appointments";
    public static final String GET_APPOINTMENT_BY_NO = "/{appointmentNo}";
}
