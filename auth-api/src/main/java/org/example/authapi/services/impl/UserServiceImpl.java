package org.example.authapi.services.impl;

import org.example.authapi.entities.User;
import org.example.authapi.repositories.UserRepository;
import org.example.authapi.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public boolean isUserExist(String email) {
        return userRepository.existsUserByEmail(email);
    }
}
