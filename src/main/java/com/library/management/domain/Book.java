package com.library.management.domain;

public class Book {
    private final String isbn;
    private String title;
    private String author;
    private String genre;
    private boolean available;

    public Book(String isbn, String title, String author, String genre) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title cannot be null or blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author cannot be null or blank");
        }
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true; // By default, the book is available
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return available;
    }
}
