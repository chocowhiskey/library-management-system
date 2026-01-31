package com.library.management.repository;

import com.library.management.domain.LibraryUser;

public interface UserRepository {
    void save(LibraryUser user);

    LibraryUser findById(String id);

    void delete(String id);
}
