package com.sm.petwellnessplus.services.user;

import java.util.List;

import com.sm.petwellnessplus.dto.UserDto;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.requests.UserUpdateRequest;

public interface IUserService {
    public User register(RegistrationRequest request);

    public User update(Long UserId, UserUpdateRequest request);

    public User findById(Long userId);

    public void delete(Long userId);

    public List<UserDto> getAllUsers();
}
