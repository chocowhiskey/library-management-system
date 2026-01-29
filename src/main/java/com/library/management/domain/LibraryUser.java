package com.library.management.domain;

import java.util.Objects;

public class LibraryUser {
    private final String id;
    private String name;
    private UserRole role;

    public LibraryUser(String id, String name, UserRole role) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isLibrarian() {
        return this.role == UserRole.LIBRARIAN;
    }

    public boolean isMember() {
        return this.role == UserRole.MEMBER;
    }
}
