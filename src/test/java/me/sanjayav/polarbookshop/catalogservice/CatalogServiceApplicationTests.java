package me.sanjayav.polarbookshop.catalogservice;


import java.time.Instant;
import me.sanjayav.polarbookshop.catalogservice.domain.Book;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = Book.of("1231231231", "Title", "Author", 9.90, "Manning");

		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.id").isNumber()
				.jsonPath("$.isbn").isEqualTo("1231231231")
				.jsonPath("$.title").isEqualTo("Title")
				.jsonPath("$.author").isEqualTo("Author")
				.jsonPath("$.publisher").isEqualTo("Manning")
				.jsonPath("$.price").isEqualTo(9.9)
				.jsonPath("$.createdDate").value(Matchers.isA(String.class))
				.jsonPath("$.lastModifiedDate").value(Matchers.isA(String.class))
				.jsonPath("$.version").isNumber();
	}
}
