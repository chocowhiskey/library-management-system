package com.library.management.service;

import com.library.management.domain.Book;
import com.library.management.domain.LibraryUser;
import com.library.management.repository.BookRepository;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book, LibraryUser librarian) {
        if (!librarian.isLibrarian()) {
            throw new IllegalArgumentException("Only librarians can add books");
        }
        bookRepository.save(book);
    }

    public void removeBook(String isbn, LibraryUser librarian) {
        if (!librarian.isLibrarian()) {
            throw new IllegalArgumentException("Only librarians can remove books");
        }
        bookRepository.deleteByIsbn(isbn);
    }

    public Book getBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with ISBN: " + isbn);
        }
        return book;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByGenre(String genre) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getGenre() != null && book.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
