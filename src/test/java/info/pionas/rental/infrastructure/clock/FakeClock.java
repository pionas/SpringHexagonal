package info.pionas.rental.infrastructure.clock;

import info.pionas.rental.domain.clock.Clock;

import java.time.LocalDateTime;

public class FakeClock implements Clock {
    public static final LocalDateTime NOW = LocalDateTime.of(2020, 10, 11, 12, 13);

    @Override
    public LocalDateTime now() {
        return NOW;
    }
}