package com.sm.petwellnessplus.services.user;

import java.util.List;

import com.sm.petwellnessplus.dto.UserDto;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.requests.UserUpdateRequest;

public interface IUserService {
    public User registerUser(RegistrationRequest request);

    public User updateUser(Long UserId, UserUpdateRequest request);

    public User getUserById(Long userId);

    public void deleteUser(Long userId);

    public List<UserDto> getAllUsers();
}
