package com.library.management.service;

import com.library.management.domain.Book;
import com.library.management.repository.BookRepository;
import java.util.List;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
