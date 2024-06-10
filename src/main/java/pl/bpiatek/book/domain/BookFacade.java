package pl.bpiatek.book.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.bpiatek.book.dto.CreateBookRequest;
import pl.bpiatek.book.dto.BookResponse;
import pl.bpiatek.book.dto.GetBooksRequest;
import pl.bpiatek.book.dto.GetBooksResponse;

@ApplicationScoped
public class BookFacade {

    @Inject
    BookDao bookDao;

    public BookResponse addBook(CreateBookRequest bookRequest) {
        var bookToSave = BookMapper.toEntity(bookRequest);
        var savedBook = bookDao.addBook(bookToSave);

        return BookMapper.toCreateBookResponse(savedBook);
    }

    public GetBooksResponse getBooks(GetBooksRequest request) {
        var books = bookDao.getBooks(request);

        return BookMapper.toGetBooksResponse(books);
    }
}
