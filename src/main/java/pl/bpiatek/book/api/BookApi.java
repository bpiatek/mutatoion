package pl.bpiatek.book.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.bpiatek.book.domain.BookFacade;
import pl.bpiatek.book.dto.CreateBookRequest;
import pl.bpiatek.book.dto.GetBooksRequest;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookApi {

    private final BookFacade bookFacade;

    BookApi(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @POST
    public Response addBook(CreateBookRequest request) {
        var createdBook = bookFacade.addBook(request);
        return Response.ok(createdBook).status(CREATED).build();
    }

    @GET
    public Response getBooks(GetBooksRequest request) {
        var books = bookFacade.getBooks(request);
        return Response.ok(books).build();
    }
}
