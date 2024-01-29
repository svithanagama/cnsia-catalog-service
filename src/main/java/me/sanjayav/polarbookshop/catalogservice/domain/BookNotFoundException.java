package me.sanjayav.polarbookshop.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super(String.format("A book with ISBN %s was not found.", isbn));
    }
}
