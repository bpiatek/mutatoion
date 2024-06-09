package pl.bpiatek.book.domain;

import jakarta.enterprise.context.ApplicationScoped;
import pl.bpiatek.book.dto.GetBooksRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static pl.bpiatek.book.dto.GetBooksRequest.SortBy.AUTHOR;
import static pl.bpiatek.book.dto.GetBooksRequest.SortBy.TITLE;

@ApplicationScoped
class BookDao {

    private static final Map<Long, Book> DATABASE = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    Book addBook(Book book) {
        var id = ID_GENERATOR.getAndIncrement();
        book.setId(id);
        DATABASE.put(id, book);

        return book;
    }

    List<Book> getBooks(GetBooksRequest request) {
        var books = request.ids().stream()
                .map(DATABASE::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (request.sortBy() == AUTHOR) {
            books.sort(comparing(Book::getAuthor));
        } else if (request.sortBy() == TITLE) {
            books.sort(comparing(Book::getTitle));
        }

        return books;
    }
}
