package org.example.authapi.services;

import org.example.authapi.entities.User;

public interface UserService {
    User createUser(User user);
    User getUserByEmail(String email);
    boolean isUserExist(String email);
}
