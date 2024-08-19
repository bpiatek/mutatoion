package pl.bpiatek.book.domain;

import java.util.Objects;

class Book {
    private Long id;
    private String title;
    private String author;
    private Integer year;

    Book() {
    }

    Book(String title, String author, Integer year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Book setYear(Integer year) {
        this.year = year;
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(year, book.year);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, title, author, year);
    }
}
