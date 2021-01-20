package info.pionas.rental.application.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.BDDMockito.then;

class ApartmentApplicationServiceTest {
    private static final ApartmentRepository apartmentRepository = Mockito.mock(ApartmentRepository.class);
    private static final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private static final EventChannel eventChannel = Mockito.mock(EventChannel.class);
    private static final ApartmentApplicationService service = new ApartmentApplicationService(apartmentRepository, bookingRepository, eventChannel);
    private final ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);

    @Test
    void shouldCreateApartmentWithAllInformation() {
        String ownerId = "123";
        String street = "Florianska";
        String postalCode = "12-345";
        String houseNumber = "1";
        String apartmentNumber = "123";
        String city = "Cracow";
        String country = "Poland";
        String description = "Room with jacuzzi";
        Map<String, Double> roomsDefinition = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

        service.add(ownerId, street, postalCode, houseNumber, apartmentNumber, city, country, description, roomsDefinition);

        then(apartmentRepository).should().save(captor.capture());
    }
}