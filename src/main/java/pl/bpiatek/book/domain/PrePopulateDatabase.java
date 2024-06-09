package pl.bpiatek.book.domain;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
class PrePopulateDatabase {

    private final BookDao bookDao;

    PrePopulateDatabase(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Startup
    void init() {
        System.out.println("Inserting books test data...");
        prepareInsertData().forEach(bookDao::addBook);
        System.out.println("Inserted books successfully!");
    }

    private List<Book> prepareInsertData() {
        return List.of(
                new Book("Effective Java", "Joshua Bloch"),
                new Book("Clean Code", "Robert C. Martin"),
                new Book("Java: The Complete Reference", "Herbert Schildt"),
                new Book("Head First Java", "Kathy Sierra and Bert Bates"),
                new Book("Java Performance: The Definitive Guide", "Scott Oaks"),
                new Book("Core Java Volume I--Fundamentals", "Cay S. Horstmann"),
                new Book("Java SE 8 for the Really Impatient", "Cay S. Horstmann"),
                new Book("Java 8 in Action", "Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft"),
                new Book("Java Puzzlers: Traps, Pitfalls, and Corner Cases", "Joshua Bloch, Neal Gafter"),
                new Book("Java Cookbook", "Ian F. Darwin"),
                new Book("Pro JavaFX 8", "James Weaver, Johan Vos, Weiqi Gao, Stephen Chin, Dean Iverson"),
                new Book("Spring in Action", "Craig Walls"),
                new Book("Java Concurrency in Practice", "Brian Goetz"));
    }
}
