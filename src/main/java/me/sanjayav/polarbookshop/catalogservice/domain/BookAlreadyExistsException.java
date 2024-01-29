package me.sanjayav.polarbookshop.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super(String.format("Book with ISBN %s already exists.", isbn));
    }
}
