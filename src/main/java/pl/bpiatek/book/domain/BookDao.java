package pl.bpiatek.book.domain;

import jakarta.enterprise.context.ApplicationScoped;
import pl.bpiatek.book.dto.GetBooksRequest;

import java.util.ArrayList;
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

    static final Map<Long, Book> DATABASE = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    Book addBook(Book book) {
        var id = ID_GENERATOR.getAndIncrement();
        book.setId(id);
        DATABASE.put(id, book);

        return book;
    }

    List<Book> getBooks(GetBooksRequest request) {
        var books = getRequestedBooks(request);

        if (request.sortBy() == AUTHOR) {
            books.sort(comparing(Book::getAuthor));
        } else if (request.sortBy() == TITLE) {
            books.sort(comparing(Book::getTitle));
        }

        if (request.newerThanYear() != null) {
            books = books
                    .stream().filter(b -> b.getYear() >= request.newerThanYear())
                    .collect(Collectors.toList());
            books.sort(comparing(Book::getYear));
        }

        return books;
    }

    private List<Book> getRequestedBooks(GetBooksRequest request) {
        if(request.ids() == null || request.ids().isEmpty()) {
            return new ArrayList<>(DATABASE.values());
        }

        return request.ids().stream()
                .map(DATABASE::get)
                .filter(Objects::nonNull)
                // this is complaining, but I want to modify this list later :)
                .collect(Collectors.toList());
    }
}
