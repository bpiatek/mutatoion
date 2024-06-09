package pl.bpiatek.book.domain;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@IfBuildProperty(name = "prepopulate.database.enabled", stringValue = "true")
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
                new Book("Java: The Complete Reference", "Herbert Schildt", 2018),
                new Book("Head First Java", "Kathy Sierra and Bert Bates", 2005),
                new Book("Java Performance: The Definitive Guide", "Scott Oaks", 2014),
                new Book("Core Java Volume I--Fundamentals", "Cay S. Horstmann", 2018),
                new Book("Java SE 8 for the Really Impatient", "Cay S. Horstmann", 2014),
                new Book("Java 8 in Action", "Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft", 2014),
                new Book("Java Puzzlers: Traps, Pitfalls, and Corner Cases", "Joshua Bloch, Neal Gafter", 2005),
                new Book("Java Cookbook", "Ian F. Darwin", 2020),
                new Book("Pro JavaFX 8", "James Weaver, Johan Vos, Weiqi Gao, Stephen Chin, Dean Iverson", 2014),
                new Book("Spring in Action", "Craig Walls", 2018));
    }
}