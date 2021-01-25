package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentApplicationServiceFactory {
    @Bean
    public ApartmentApplicationService create(ApartmentRepository apartmentRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        return new ApartmentApplicationService(apartmentRepository, bookingRepository, eventChannel);
    }
}
