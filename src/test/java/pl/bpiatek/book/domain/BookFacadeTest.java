package pl.bpiatek.book.domain;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.bpiatek.book.dto.BookResponse;
import pl.bpiatek.book.dto.CreateBookRequest;
import pl.bpiatek.book.dto.GetBooksRequest;
import pl.bpiatek.book.dto.GetBooksResponse;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static pl.bpiatek.book.dto.GetBooksRequest.SortBy.AUTHOR;
import static pl.bpiatek.book.dto.GetBooksRequest.SortBy.TITLE;

@QuarkusTest
class BookFacadeTest extends DatabaseTestContext {

    @Inject BookFacade bookFacade;
    @Inject BookTestFixtures bookTestFixtures;

    @Test
    void shouldCreateBook() {
        // given
        var request = new CreateBookRequest("title", "author", 2021);

        // when
        var response = bookFacade.addBook(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.id()).isNotNull();
            softly.assertThat(response.title()).isEqualTo(request.title());
            softly.assertThat(response.author()).isEqualTo(request.author());
            softly.assertThat(response.year()).isEqualTo(request.year());
        });
    }

    @Test
    void shouldGetAllBooks() {
        // given
        var book1 = bookTestFixtures.aBook();
        var book2 = bookTestFixtures.aBook();

        var request = new GetBooksRequest(List.of(), null, null);

        // when
        var response = bookFacade.getBooks(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.books()).hasSize(2);
            softly.assertThat(booksIds(response))
                    .containsExactlyInAnyOrder(book1.getId(), book2.getId());
        });
    }

    @Test
    void shouldGetBooksById() {
        // given
        var book = bookTestFixtures.aBook();
        bookTestFixtures.aBook();

        var request = new GetBooksRequest(List.of(book.getId()), null, null);

        // when
        var response = bookFacade.getBooks(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.books()).hasSize(1);
            softly.assertThat(response.books().getFirst().id()).isEqualTo(book.getId());
        });
    }

    @Test
    void shouldGetBooksAndSortByTitle() {
        // given
        var book1 = bookTestFixtures.aBook(new CreateBookRequest("a title", "author", 2021));
        var book2 = bookTestFixtures.aBook(new CreateBookRequest("z title", "author", 2021));

        var request = new GetBooksRequest(null, TITLE, null);

        // when
        var response = bookFacade.getBooks(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.books()).hasSize(2);
            softly.assertThat(booksIds(response))
                    .containsExactly(book1.getId(), book2.getId());
        });
    }

    @Test
    void shouldGetBooksAndSortByAuthor() {
        // given
        var book1 = bookTestFixtures.aBook(new CreateBookRequest("z title", "a author", 2021));
        var book2 = bookTestFixtures.aBook(new CreateBookRequest("a title", "b author", 2021));

        var request = new GetBooksRequest(null, AUTHOR, null);

        // when
        var response = bookFacade.getBooks(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.books()).hasSize(2);
            softly.assertThat(booksIds(response))
                .containsExactly(book1.getId(), book2.getId());
        });
    }

    @Test
    void shouldFilterOutBooksOlderThanYear() {
        // given
        bookTestFixtures.aBook(new CreateBookRequest("z title", "z author", 2020));
        var book2 = bookTestFixtures.aBook(new CreateBookRequest("c title", "c author", 2021));
        var book3= bookTestFixtures.aBook(new CreateBookRequest("a title", "a author", 2024));

        var request = new GetBooksRequest(null, null, 2021);

        // when
        var response = bookFacade.getBooks(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.books()).hasSize(2);
            softly.assertThat(booksIds(response))
                .containsExactly(book2.getId(), book3.getId());
        });
    }

    private static List<Long> booksIds(GetBooksResponse response) {
        return response.books().stream().map(BookResponse::id).toList();
    }
}