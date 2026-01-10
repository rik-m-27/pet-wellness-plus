package com.petwellnessplus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.petwellnessplus.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<String>> handleBadRequest(IllegalArgumentException ex){
		ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST,ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
		
        ApiResponse<String> response = ApiResponse.error(HttpStatus.FORBIDDEN, "You do not have the access");
        
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiResponse<String>> handleIllegalState(IllegalStateException ex) {
	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex){
		ApiResponse<String> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
