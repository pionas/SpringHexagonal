package info.pionas.rental.infrastructure.commandregistry.spring;

import info.pionas.rental.application.booking.BookingAccept;
import info.pionas.rental.application.booking.BookingReject;
import info.pionas.rental.application.commandregisty.CommandRegisty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Adi
 */
@Component
@RequiredArgsConstructor
class SpringCommandRegisty implements CommandRegisty {

    private final ApplicationEventPublisher publisher;

    @Override
    public void register(BookingReject bookingReject) {
        publisher.publishEvent(bookingReject);
    }

    @Override
    public void register(BookingAccept bookingAccept) {
        publisher.publishEvent(bookingAccept);
    }

}
