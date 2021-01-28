package info.pionas.rental.infrastructure.clock;

import info.pionas.rental.domain.clock.Clock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class LocalClock implements Clock {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}