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

        // 5. Versuch: Member versucht Buch hinzuzufügen (sollte fehlschlagen!)
        System.out.println("--- Testing Business Rule: Member cannot add books ---");
        try {
            Book unauthorizedBook = new Book("978-1-234-56789-0", "Unauthorized Book", "Hacker", "Fiction");
            bookService.addBook(unauthorizedBook, member1);
            System.out.println("✗ ERROR: Member was able to add book (should not happen!)");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly blocked: " + e.getMessage());
        }
        System.out.println();

        // 6. Buch ausleihen
        System.out.println("--- Borrowing Books ---");
        Loan loan1 = loanService.borrowBook(book1.getIsbn(), member1);
        System.out.println("✓ " + member1.getName() + " borrowed: " + book1.getTitle());
        System.out.println("  Loan date: " + loan1.getLoanDate());
        System.out.println("  Book available: " + book1.isAvailable());
        System.out.println();

        Loan loan2 = loanService.borrowBook(book2.getIsbn(), member2);
        System.out.println("✓ " + member2.getName() + " borrowed: " + book2.getTitle());
        System.out.println();

        // 7. Versuch: Gleiches Buch nochmal ausleihen (sollte fehlschlagen!)
        System.out.println("--- Testing Business Rule: Cannot borrow unavailable book ---");
        try {
            loanService.borrowBook(book1.getIsbn(), member2);
            System.out.println("✗ ERROR: Was able to borrow unavailable book!");
        } catch (IllegalStateException e) {
            System.out.println("✓ Correctly blocked: " + e.getMessage());
        }
        System.out.println();

        // 8. Buch zurückgeben
        System.out.println("--- Returning Books ---");
        loanService.returnBook(book1.getIsbn());
        System.out.println("✓ Returned: " + book1.getTitle());
        System.out.println("  Book available: " + book1.isAvailable());
        System.out.println("  Loan active: " + loan1.isActive());
        System.out.println("  Return date: " + loan1.getReturnDate());
        System.out.println();
    }

}