package com.library.management;

import com.library.management.domain.Book;
import com.library.management.domain.LibraryUser;
import com.library.management.domain.Loan;
import com.library.management.domain.UserRole;
import com.library.management.repository.BookRepository;
import com.library.management.repository.InMemoryBookRepository;
import com.library.management.repository.InMemoryLoanRepository;
import com.library.management.repository.InMemoryUserRepository;
import com.library.management.repository.LoanRepository;
import com.library.management.repository.UserRepository;
import com.library.management.service.BookService;
import com.library.management.service.LoanService;
import com.library.management.service.UserService;

public class LibraryApplication {

    public static void main(String[] args) {
        System.out.println("=== Library Management System ===\n");

        // 1. Repositories erstellen
        BookRepository bookRepository = new InMemoryBookRepository();
        UserRepository userRepository = new InMemoryUserRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        // 2. Services initialisieren
        BookService bookService = new BookService(bookRepository);
        UserService userService = new UserService(userRepository);
        LoanService loanService = new LoanService(bookRepository, loanRepository);

        System.out.println("✓ System initialized\n");

        // 3. User erstellen
        System.out.println("--- Creating Users ---");
        LibraryUser librarian = new LibraryUser("L001", "Alice", UserRole.LIBRARIAN);
        LibraryUser member1 = new LibraryUser("M001", "Bob", UserRole.MEMBER);
        LibraryUser member2 = new LibraryUser("M002", "Charlie", UserRole.MEMBER);

        userService.addUser(librarian);
        userService.addUser(member1);
        userService.addUser(member2);

        System.out.println("✓ Created: " + librarian);
        System.out.println("✓ Created: " + member1);
        System.out.println("✓ Created: " + member2);
        System.out.println();

        // 4. Bücher hinzufügen (als Librarian)
        System.out.println("--- Adding Books (as Librarian) ---");
        Book book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch", "Programming");
        Book book2 = new Book("978-0-13-235088-4", "Clean Code", "Robert Martin", "Programming");
        Book book3 = new Book("978-0-201-63361-0", "Design Patterns", "Gang of Four", "Software Engineering");

        bookService.addBook(book1, librarian);
        bookService.addBook(book2, librarian);
        bookService.addBook(book3, librarian);

        System.out.println("✓ Added: " + book1.getTitle());
        System.out.println("✓ Added: " + book2.getTitle());
        System.out.println("✓ Added: " + book3.getTitle());
        System.out.println();
    }

}