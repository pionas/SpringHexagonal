package info.pionas.rental.domain.money;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

class MoneyTest {
    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        Money actual = givenMoney();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsMoney() {
        Money actual = givenMoney();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameMoneys")
    void shouldRecognizeMoneysDoesNotRepresentTheSameAggregate(Object toCompare) {
        Money actual = givenMoney();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private Money givenMoney() {
        return Money.of(BigDecimal.valueOf(100));
    }

    private static Stream<Object> notTeSameMoneys() {
        return Stream.of(
                Money.of(BigDecimal.valueOf(10)),
                new Object()
        );
    }
}