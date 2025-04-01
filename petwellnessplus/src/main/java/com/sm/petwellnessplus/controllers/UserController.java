package com.sm.petwellnessplus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.petwellnessplus.dto.EntityConverter;
import com.sm.petwellnessplus.dto.UserDto;
import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.exceptions.UserAlreadyExistsException;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.requests.UserUpdateRequest;
import com.sm.petwellnessplus.response.ApiResponse;
import com.sm.petwellnessplus.services.user.UserService;
import com.sm.petwellnessplus.utils.FeedBackMessage;

import lombok.RequiredArgsConstructor;
import com.sm.petwellnessplus.utils.UrlMapping;

@RequiredArgsConstructor
@RequestMapping(UrlMapping.USER)
@RestController
public class UserController {

	private final UserService userService;
	private final EntityConverter<User, UserDto> entityConverter;

	@PostMapping(UrlMapping.REGISTER_USER)
	public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest request) {
		try {
			User theUser = userService.register(request);
			UserDto registeredUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse(FeedBackMessage.SUCCESS, registeredUser));
		} catch (UserAlreadyExistsException uex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(uex.getMessage(), null));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(ex.getMessage(), null));
		}
	}

	@PutMapping(UrlMapping.UPDATE_USER)
	public ResponseEntity<ApiResponse> update(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
		try {
			User theUser = userService.update(userId, request);
			UserDto updatedUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updatedUser));
		} catch (ResourceNotFoundException rnfex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(rnfex.getMessage(), null));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(ex.getMessage(), null));
		}
	}

	@GetMapping(UrlMapping.GET_USER_BY_ID)
	public ResponseEntity<ApiResponse> findById(@PathVariable Long userId) {
		try {
			User theUser = userService.findById(userId);
			UserDto fetchedUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(FeedBackMessage.FOUND, fetchedUser));

		} catch (ResourceNotFoundException rnfex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(rnfex.getMessage(), null));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(ex.getMessage(), null));
		}
	}

	@DeleteMapping(UrlMapping.DELETE_USER_BY_ID)
	public ResponseEntity<ApiResponse> delete(@PathVariable Long userId) {
		try {
			userService.delete(userId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, null));
		} catch (ResourceNotFoundException rnfex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(rnfex.getMessage(), null));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(ex.getMessage(), null));
		}
	}

	@GetMapping(UrlMapping.GET_ALL_USERS)
	public ResponseEntity<ApiResponse> getAllUsers() {
		try {
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ApiResponse(FeedBackMessage.FOUND, userService.getAllUsers()));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(ex.getMessage(), null));
		}
	}

}
