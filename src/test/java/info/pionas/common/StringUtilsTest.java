package info.pionas.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilsTest {

    @Test
    void shouldGenerateRandomString() {
        String actual = StringUtils.generateString(10);
        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> StringUtils.generateString(-1));
        assertThat(actual).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRecognizeStringIsEmpty() {
        boolean actual = StringUtils.isNoneEmpty("");
        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeStringDoesNotEmpty() {
        boolean actual = StringUtils.isNoneEmpty("xxx");
        assertThat(actual).isTrue();
    }
}