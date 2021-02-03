package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.ApartmentDomainService;
import info.pionas.rental.domain.apartment.ApartmentEventsPublisher;
import info.pionas.rental.domain.apartment.ApartmentFactory;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.owner.OwnerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApartmentApplicationServiceFactory {
    @Bean
    @SuppressWarnings("checkstyle:ParameterNumber")
    ApartmentApplicationService apartmentApplicationService(
            ApartmentRepository apartmentRepository, BookingRepository bookingRepository,
            OwnerRepository ownerRepository, EventIdFactory eventIdFactory,
            Clock clock, EventChannel eventChannel) {
        ApartmentEventsPublisher apartmentEventsPublisher = new ApartmentEventsPublisher(eventIdFactory, clock, eventChannel);
        ApartmentFactory apartmentFactory = new ApartmentFactory(ownerRepository);
        ApartmentDomainService apartmentDomainService = new ApartmentDomainService(apartmentRepository, apartmentEventsPublisher);
        return new ApartmentApplicationService(apartmentRepository, bookingRepository, apartmentFactory, apartmentDomainService);
    }
}
