package pl.bpiatek.book.domain;

import pl.bpiatek.book.dto.CreateBookRequest;
import pl.bpiatek.book.dto.BookResponse;
import pl.bpiatek.book.dto.GetBooksResponse;

import java.util.List;

class BookMapper {

    private BookMapper() {
    }

    static Book toEntity(CreateBookRequest request) {
        return new Book()
                .setAuthor(request.author())
                .setTitle(request.title())
                .setYear(request.year());
    }

    static BookResponse toCreateBookResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
    }

    static GetBooksResponse toGetBooksResponse(List<Book> books) {
        var bookResponses = books.stream().map(BookMapper::toCreateBookResponse).toList();
        return new GetBooksResponse(bookResponses);
    }
}
