package com.library.management.repository;

import com.library.management.domain.Book;
import java.util.List;

public interface BookRepository {

    void save(Book book);

    Book findByIsbn(String isbn);

    List<Book> findAll();

    void deleteByIsbn(String isbn);

}
