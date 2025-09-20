package me.sanjayav.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import me.sanjayav.polarbookshop.catalogservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(
    replace = Replace.NONE
)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  void findBookByIsbnWhenExisting() {
    var bookIsbn = "1234561237";
    var book = Book.of(bookIsbn, "Title", "Author", 12.90, "Manning");
    jdbcAggregateTemplate.insert(book);
    Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);
    assertThat(actualBook).isPresent();
    assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
  }

  @Test
  void whenCreateBookNotAuthenticatedThenNoAuditMetadata() {
    var bookToCreate = Book.of("1232343456", "Title", "Author", 12.90, "Polarsophia");
    var createdBook = bookRepository.save(bookToCreate);
    assertThat(createdBook.createdBy()).isNull();
    assertThat(createdBook.lastModifiedBy()).isNull();
  }

  @Test
  @WithMockUser("john")
  void whenCreateBookAuthenticatedThenAuditMetadata() {
    var bookToCreate = Book.of("1232343457", "Title", "Author", 12.90, "Polarsophia");
    var createdBook = bookRepository.save(bookToCreate);
    assertThat(createdBook.createdBy()).isEqualTo("john");
    assertThat(createdBook.lastModifiedBy()).isEqualTo("john");
  }
}
