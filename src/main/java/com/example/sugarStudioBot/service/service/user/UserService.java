package com.example.sugarStudioBot.service.service.user;

import com.example.sugarStudioBot.service.dto.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(long id, User user);

    List<User> getAllUsers();

    void deleteUserById(long userId);
}
