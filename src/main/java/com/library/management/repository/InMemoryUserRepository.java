package com.library.management.repository;

import com.library.management.domain.LibraryUser;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, LibraryUser> users = new HashMap<>();

    @Override
    public void save(LibraryUser user) {
        users.put(user.getId(), user);
    }

    @Override
    public LibraryUser findById(String id) {
        return users.get(id);
    }

    @Override
    public void delete(String id) {
        users.remove(id);
    }

}
