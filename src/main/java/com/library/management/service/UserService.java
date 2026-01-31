package com.library.management.service;

import com.library.management.domain.LibraryUser;
import com.library.management.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(LibraryUser user) {
        userRepository.save(user);
    }

    public LibraryUser getUser(String id) {
        LibraryUser user = userRepository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        return user;
    }

    public void removeUser(String id) {
        userRepository.delete(id);
    }

}