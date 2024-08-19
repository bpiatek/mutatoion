package pl.bpiatek.book.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.bpiatek.book.dto.CreateBookRequest;


@ApplicationScoped
public class BookTestFixtures {

    @Inject
    BookFacade bookFacade;

    public void insertBook(CreateBookRequest request) {
        bookFacade.addBook(request);
    }

    Book aBook() {
       var response = bookFacade.addBook(
               new CreateBookRequest(
                       "some title",
                       "some author",
                       2003));

       return new Book(response.title(), response.author(), response.year())
               .setId(response.id());
   }

    Book aBook(CreateBookRequest request) {
        var response = bookFacade.addBook(request);

        return new Book(response.title(), response.author(), response.year())
                .setId(response.id());
    }
}
