package me.sanjayav.polarbookshop.catalogservice.domain;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BookRepository {

    Iterable<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    Book save(Book book);

    void deleteByIsbn(String isbn);

}
