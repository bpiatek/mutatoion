package pl.bpiatek.book.domain;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pl.bpiatek.book.dto.CreateBookRequest;

@QuarkusTest
class BookFacadeTest {

    @Inject
    BookFacade bookFacade;

    @Test
    void shouldCreateBook() {
        // given
        var request = new CreateBookRequest("title", "author");

        // when
        var response = bookFacade.addBook(request);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.id()).isNotNull();
            softly.assertThat(response.title()).isEqualTo("title");
            softly.assertThat(response.author()).isEqualTo("author");
        });
    }
}