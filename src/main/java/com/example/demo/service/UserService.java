package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findUserByUsername(String username);

    boolean saveUser(User user);

    boolean deleteUser(Long id, String password);

    boolean checkConfirmation(String token);

    boolean changeRole(Long id);

    boolean changePassword(Long id, String newPassword, String newPasswordConfirm, String oldPassword);

    boolean changeEmail(Long id, String newEmail, String newEmailConfirm, String password);
}
