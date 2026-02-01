package com.library.management.service;

import com.library.management.domain.Book;
import com.library.management.domain.LibraryUser;
import com.library.management.domain.Loan;
import com.library.management.domain.UserRole;
import com.library.management.repository.BookRepository;
import com.library.management.repository.InMemoryBookRepository;
import com.library.management.repository.InMemoryLoanRepository;
import com.library.management.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    private LoanService loanService;
    private BookRepository bookRepository;
    private LoanRepository loanRepository;
    private Book book;
    private LibraryUser user;

    @BeforeEach
    void setUp() {
        bookRepository = new InMemoryBookRepository();
        loanRepository = new InMemoryLoanRepository();
        loanService = new LoanService(bookRepository, loanRepository);

        book = new Book("978-3-16-148410-0", "Clean Code", "Robert Martin", "Programming");
        bookRepository.save(book);

        user = new LibraryUser("1", "Max", UserRole.MEMBER);
    }

    @Test
    void borrowBookSuccessfully() {
        Loan loan = loanService.borrowBook(book.getIsbn(), user);

        assertNotNull(loan);
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertFalse(book.isAvailable());
        assertTrue(loan.isActive());
    }

    @Test
    void cannotBorrowUnavailableBook() {
        loanService.borrowBook(book.getIsbn(), user);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            loanService.borrowBook(book.getIsbn(), user);
        });

        assertEquals("Book is not available for borrowing", exception.getMessage());
    }

    @Test
    void returnBookSuccessfully() {
        Loan loan = loanService.borrowBook(book.getIsbn(), user);

        loanService.returnBook(book.getIsbn());

        assertTrue(book.isAvailable());
        assertFalse(loan.isActive());
        assertNotNull(loan.getReturnDate());
    }

    @Test
    void cannotReturnBookNotBorrowed() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            loanService.returnBook(book.getIsbn());
        });

        assertEquals("No active loan found for ISBN: " + book.getIsbn(), exception.getMessage());
    }

}