package info.pionas.rental.infrastructure.clock;

import info.pionas.rental.domain.clock.Clock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalClockTest {
    private Clock clock = new LocalClock();

    @Test
    void shouldReturnNowDateTime() {
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);

        LocalDateTime actual = clock.now();

        assertThat(actual)
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
    }
}