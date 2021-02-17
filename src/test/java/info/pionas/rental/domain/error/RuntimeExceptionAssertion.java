package info.pionas.rental.domain.error;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RuntimeExceptionAssertion {
    private final RuntimeException actual;

    public static RuntimeExceptionAssertion assertThat(RuntimeException actual) {
        return new RuntimeExceptionAssertion(actual);
    }

    public RuntimeExceptionAssertion hasMessage(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("message", expected);
        return this;
    }
}
