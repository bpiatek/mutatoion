package pl.bpiatek.book.dto;

import java.util.List;

public record GetBooksRequest(List<Long> ids, SortBy sortBy, Integer newerThanYear) {
    public enum SortBy {
        AUTHOR,
        TITLE
    }
}
