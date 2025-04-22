package com.sm.petwellnessplus.services.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.dto.EntityConverter;
import com.sm.petwellnessplus.dto.UserDto;
import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.factories.UserFactory;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.repositories.UserRepository;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.requests.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final UserRepository userRepository;
	private final UserFactory userFactory;
	private final EntityConverter<User, UserDto> entityConverter;

	@Override
	public User registerUser(RegistrationRequest request) {
		return userFactory.createUser(request);
	}

	@Override
	public User updateUser(Long userId, UserUpdateRequest request) {
		User user = getUserById(userId);
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setGender(request.getGender());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setSpecialization(request.getSpecialization());

		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId)
				.ifPresentOrElse(userRepository::delete, () -> {
					throw new ResourceNotFoundException("User not found");
				});
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> entityConverter.mapEntityToDto(user, UserDto.class))
				.collect(Collectors.toList());
		return userDtos;
	}
}
