package pl.bpiatek.book.domain;


import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.simple().forClass(Book.class).verify();
    }
}