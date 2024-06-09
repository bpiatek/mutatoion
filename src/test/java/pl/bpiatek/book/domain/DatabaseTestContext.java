package pl.bpiatek.book.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;

import static pl.bpiatek.book.domain.BookDao.DATABASE;

@QuarkusTest
public class DatabaseTestContext {

    @AfterEach
    void clearDatabase() {
        DATABASE.clear();
    }
}
