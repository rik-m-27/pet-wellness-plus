package com.petwellnessplus.dto;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    @JsonFormat(
	  pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
	  timezone = "UTC"
	)
    private OffsetDateTime dateTimeWithZone;

    private AppResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dateTimeWithZone = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public static <T> AppResponse<T> getResponse(HttpStatus status, T data) {
        return new AppResponse<>(status.value(), status.getReasonPhrase(), data);
    }
    
    public static <T> AppResponse<T> informational(HttpStatus status, T data) {
    	if(!status.is1xxInformational()){
    		throw new IllegalArgumentException("Must provide an informational status code 1xx");
    	}
    	return new AppResponse<>(status.value(), status.getReasonPhrase(), data);
    }
    
    public static <T> AppResponse<T> success(HttpStatus status, T data) {
    	if(!status.is2xxSuccessful()) {
    		throw new IllegalArgumentException("Must provide an success status code 2xx");
    	}
    	return new AppResponse<>(status.value(), status.getReasonPhrase(), data);
    }
    
    public static <T> AppResponse<T> redirection(HttpStatus status, T data) {
    	if(!status.is3xxRedirection()) {
    		throw new IllegalArgumentException("Must provide an redirection status code 3xx");
    	}
    	return new AppResponse<>(status.value(), status.getReasonPhrase(), data);
    }
    
    public static <T> AppResponse<T> error(HttpStatus status, T data) {
    	if(!status.isError()) {
    		throw new IllegalArgumentException("Must provide an error status code (4xx or 5xx)");
    	}
    	return new AppResponse<>(status.value(), status.getReasonPhrase(), data);
    }

    public int getStatusCode() {
    	return statusCode;
    }
    public String getMessage() {
    	return message;
    }
    public T getData() {
    	return data;
    }
    public OffsetDateTime getDateTimeWithZone() {
    	return dateTimeWithZone;
    }
}