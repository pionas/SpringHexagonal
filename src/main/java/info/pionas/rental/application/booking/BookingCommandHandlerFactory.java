package info.pionas.rental.application.booking;

import info.pionas.rental.domain.agreement.AgreementRepository;
import info.pionas.rental.domain.booking.BookingDomainService;
import info.pionas.rental.domain.booking.BookingDomainServiceFactory;
import info.pionas.rental.domain.booking.BookingEventsPublisher;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookingCommandHandlerFactory {
    @SuppressWarnings("checkstyle:ParameterNumber")
    @Bean
    BookingCommandHandler bookingCommandHandler(
            BookingRepository bookingRepository,
            AgreementRepository agreementRepository,
            EventIdFactory eventIdFactory,
            Clock clock,
            EventChannel eventChannel) {
        BookingDomainService bookingDomainService = new BookingDomainServiceFactory().create(eventIdFactory, clock, eventChannel);
        return new BookingCommandHandler(
                bookingRepository,
                agreementRepository,
                bookingDomainService,
                new BookingEventsPublisher(eventIdFactory, clock, eventChannel)
        );
    }
}
