package pl.bpiatek.book.dto;

import java.util.List;

public record GetBooksResponse(List<BookResponse> books) {
}
