package pl.bpiatek.book.domain;


import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier
            .forClass(Book.class)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }
}