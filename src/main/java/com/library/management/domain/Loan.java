package com.library.management.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private final Book book;
    private final LibraryUser user;
    private final LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Book book, LibraryUser user, LocalDate loanDate) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (loanDate == null) {
            throw new IllegalArgumentException("Loan date cannot be null");
        }
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public Book getBook() {
        return book;
    }

    public LibraryUser getUser() {
        return user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void returnDate(LocalDate returnDate) {
        if (returnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null");
        }

        if (this.returnDate != null) {
            throw new IllegalStateException("Book has already been returned");
        }

        this.returnDate = returnDate;
        this.book.markAsAvailable();
    }

    public boolean isActive() {
        return this.returnDate == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Loan loan = (Loan) o;
        return Objects.equals(book, loan.book) &&
                Objects.equals(user, loan.user) &&
                Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, user, loanDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book +
                ", user=" + user +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }

}
