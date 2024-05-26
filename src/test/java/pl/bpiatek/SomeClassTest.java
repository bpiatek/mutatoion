package pl.bpiatek;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SomeClassTest {

    @Test
    void shouldDivideCorrectly() {
        // given
        var a = 4;
        var b = 2;

        // when
        var result = new SomeClass().divide(a, b);

        // then
        assertThat(result).isEqualTo(2);
    }
}