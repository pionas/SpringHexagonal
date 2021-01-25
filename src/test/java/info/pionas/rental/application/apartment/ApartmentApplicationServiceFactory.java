package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.ApartmentEventsPublisher;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApartmentApplicationServiceFactory {
    @Bean
    ApartmentApplicationService apartmentApplicationService(ApartmentRepository apartmentRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        ApartmentEventsPublisher publisher = new ApartmentEventsPublisher(new EventIdFactory(), eventChannel);

        return new ApartmentApplicationService(apartmentRepository, bookingRepository, publisher);
    }
}
