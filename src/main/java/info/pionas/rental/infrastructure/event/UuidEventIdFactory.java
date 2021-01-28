package info.pionas.rental.infrastructure.event;

import info.pionas.rental.domain.event.EventIdFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class UuidEventIdFactory implements EventIdFactory {
    @Override
    public String create() {
        return UUID.randomUUID().toString();
    }
}
