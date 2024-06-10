package pl.bpiatek.book.domain;

import org.junit.jupiter.api.AfterEach;

import static pl.bpiatek.book.domain.BookDao.DATABASE;

public class DatabaseTestContext {

    @AfterEach
    void clearDatabase() {
        DATABASE.clear();
    }
}
