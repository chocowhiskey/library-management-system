package com.library.management.repository;

import com.library.management.domain.Book;
import java.util.List;

public interface BookRepository {

    void save(Book book);

    void deleteByIsbn(String isbn);

    Book findByIsbn(String isbn);

    List<Book> findAll();

}
