package pl.bpiatek.book.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import pl.bpiatek.book.dto.CreateBookRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BookApiTest {
    @Test
    public void shouldAddBook() {
        given()
                .contentType(ContentType.JSON)
                .body(new CreateBookRequest("Effective Java", "Joshua Bloch"))
                .when().post("/books")
                .then()
                .statusCode(201)
                .body("title", is("Effective Java"))
                .body("author", is("Joshua Bloch"));
    }
}