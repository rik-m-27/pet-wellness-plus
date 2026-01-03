package com.petwellnessplus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.petwellnessplus.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle validation errors or Bad Requests
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<String>> handleBadRequest(IllegalArgumentException ex){
		ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST,ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// Catch-all for any other internal server errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex){
		ApiResponse<String> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
