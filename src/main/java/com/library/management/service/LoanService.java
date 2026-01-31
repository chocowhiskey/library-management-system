package com.library.management.service;

import com.library.management.domain.Book;
import com.library.management.domain.Loan;
import com.library.management.domain.LibraryUser;
import com.library.management.repository.BookRepository;
import com.library.management.repository.LoanRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LoanService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public Loan borrowBook(String isbn, LibraryUser user) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with ISBN: " + isbn);
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        book.markAsUnavailable();
        Loan loan = new Loan(book, user, LocalDate.now());
        loanRepository.save(loan);

        return loan;
    }

    public void returnBook(String isbn) {
        Optional<Loan> activeLoan = loanRepository.findActiveByBook(isbn);
        if (activeLoan.isEmpty()) {
            throw new IllegalStateException("No active loan found for ISBN: " + isbn);
        }

        activeLoan.get().returnDate(LocalDate.now());
    }

}