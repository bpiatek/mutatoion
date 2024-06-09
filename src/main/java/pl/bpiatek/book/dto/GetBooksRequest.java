package pl.bpiatek.book.dto;

import java.util.List;

public record GetBooksRequest(List<Long> ids, SortBy sortBy) {
    public enum SortBy {
        AUTHOR,
        TITLE
    }
}
