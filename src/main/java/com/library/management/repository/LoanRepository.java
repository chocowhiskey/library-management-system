package com.library.management.repository;

import com.library.management.domain.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);

    Optional<Loan> findActiveByBook(String isbn);

    List<Loan> findByUserId(String userId);

}
