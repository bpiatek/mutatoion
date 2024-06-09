package pl.bpiatek.book.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.bpiatek.book.domain.BookTestFixtures;
import pl.bpiatek.book.domain.DatabaseTestContext;
import pl.bpiatek.book.dto.CreateBookRequest;
import pl.bpiatek.book.dto.GetBooksRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class BookApiTest extends DatabaseTestContext {

    @Inject
    BookTestFixtures bookTestFixtures;

    @Test
    public void shouldAddBook() {
        given()
                .contentType(ContentType.JSON)
                .body(new CreateBookRequest("Effective Java", "Joshua Bloch", 2002))
                .when().post("/books")
                .then()
                .statusCode(201)
                .body("title", is("Effective Java"))
                .body("author", is("Joshua Bloch"))
                .body("year", is(2002));
    }

    @Test
    public void shouldGetBooks() {
        bookTestFixtures.insertBook(
                new CreateBookRequest(
                        "Effective Java",
                        "Joshua Bloch",
                        2002));

        given()
                .contentType(ContentType.JSON)
                .body(new GetBooksRequest(null, null, null))
                .when().get("/books")
                .then()
                .statusCode(200)
                .body("books", hasSize(1))
                .body("books[0].title", is("Effective Java"))
                .body("books[0].author", is("Joshua Bloch"))
                .body("books[0].year", is(2002));
    }
}