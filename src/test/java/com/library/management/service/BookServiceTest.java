package com.library.management.service;

import com.library.management.domain.Book;
import com.library.management.domain.LibraryUser;
import com.library.management.domain.UserRole;
import com.library.management.repository.BookRepository;
import com.library.management.repository.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private LibraryUser librarian;
    private LibraryUser member;

    @BeforeEach
    void setUp() {
        bookRepository = new InMemoryBookRepository();
        bookService = new BookService(bookRepository);

        librarian = new LibraryUser("1", "Alice", UserRole.LIBRARIAN);
        member = new LibraryUser("2", "Bob", UserRole.MEMBER);
    }

    @Test
    void librarianCanAddBook() {
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming");

        bookService.addBook(book, librarian);

        Book retrieved = bookService.getBook("978-3-16-148410-0");
        assertNotNull(retrieved);
        assertEquals("Effective Java", retrieved.getTitle());
    }

    @Test
    void memberCannotAddBook() {
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook(book, member);
        });

        assertEquals("Only librarians can add books", exception.getMessage());
    }

    @Test
    void searchByTitleFindsBooks() {
        Book book1 = new Book("978-1", "Java Basics", "Max", "Programming");
        Book book2 = new Book("978-2", "Advanced Java", "Anna", "Programming");
        Book book3 = new Book("978-3", "Python Guide", "Tom", "Programming");

        bookService.addBook(book1, librarian);
        bookService.addBook(book2, librarian);
        bookService.addBook(book3, librarian);

        var results = bookService.searchByTitle("java");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(b -> b.getTitle().equals("Java Basics")));
        assertTrue(results.stream().anyMatch(b -> b.getTitle().equals("Advanced Java")));
    }
}