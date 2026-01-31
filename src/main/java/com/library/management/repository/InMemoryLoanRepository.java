package com.library.management.repository;

import com.library.management.domain.Loan;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    private final List<Loan> loans = new ArrayList<>();

    @Override
    public void save(Loan loan) {
        loans.add(loan);
    }

    @Override
    public Optional<Loan> findActiveByBook(String isbn) {
        return loans.stream()
                .filter(loan -> loan.getBook().getIsbn().equals(isbn))
                .filter(loan -> loan.isActive())
                .findFirst();
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return loans.stream()
                .filter(loan -> loan.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

}
