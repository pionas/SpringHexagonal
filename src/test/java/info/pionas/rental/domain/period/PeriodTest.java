package info.pionas.rental.domain.period;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class PeriodTest {
    private static final LocalDate START = LocalDate.of(2020, 10, 11);
    private static final LocalDate END = LocalDate.of(2021, 10, 11);

    @ParameterizedTest
    @MethodSource("daysBetweenStartAndEnd")
    void shouldReturnAllDaysBetweenStartAndEnd(LocalDate start, LocalDate end, Iterable<LocalDate> expected) {
        List<LocalDate> actual = new Period(start, end).asDays();

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    private static Stream<Arguments> daysBetweenStartAndEnd() {
        return Stream.of(
                Arguments.of(
                        LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 3),
                        asList(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 3))),
                Arguments.of(
                        LocalDate.of(2020, 10, 1), LocalDate.of(2020, 10, 2),
                        asList(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 10, 2))),
                Arguments.of(
                        LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 10),
                        asList(
                                LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 6), LocalDate.of(2020, 5, 7),
                                LocalDate.of(2020, 5, 8), LocalDate.of(2020, 5, 9), LocalDate.of(2020, 5, 10)))
        );
    }

    @Test
    void shouldReturnOneDateWhenStartAndEndAreTheSame() {
        LocalDate date = LocalDate.of(2020, 10, 11);

        List<LocalDate> actual = new Period(date, date).asDays();

        assertThat(actual).containsExactly(date);
    }

    @Test
    void shouldCheckBeEqualWithItself() {
        Period actual = new Period(START, END);

        assertThat(actual.equals(actual)).isTrue();
        assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldCheckBeEqualWithInstanceThatHaveTheSameValues() {
        Period expected = new Period(START, END);

        Period actual = new Period(START, END);

        assertThat(actual.equals(expected)).isTrue();
        assertThat(actual.hashCode()).isEqualTo(expected.hashCode());
    }

    @ParameterizedTest
    @MethodSource("notEqualPeriods")
    void shouldNotBeEqualTo(Object expected) {
        Period actual = new Period(START, END);

        assertThat(actual.equals(expected)).isFalse();
        assertThat(actual.hashCode()).isNotEqualTo(expected.hashCode());
    }

    private static Stream<Object> notEqualPeriods() {
        return Stream.of(new Period(START, LocalDate.now()), new Period(LocalDate.now(), END), new Object());
    }

    @Test
    void shouldNotBeEqualToNull() {
        Period actual = new Period(START, END);

        assertThat(actual.equals(null)).isFalse();
    }
}