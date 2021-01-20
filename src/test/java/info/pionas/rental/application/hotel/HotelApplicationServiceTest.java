package info.pionas.rental.application.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.then;

class HotelApplicationServiceTest {
    private static final HotelRepository hotelRepository = Mockito.mock(HotelRepository.class);
    private static final HotelApplicationService service = new HotelApplicationService(hotelRepository);
    private static final String NAME = "123";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "1";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";

    @Test
    void shouldCreateApartmentWithAllInformation() {
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);
        service.add(NAME, STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);

        then(hotelRepository).should().save(captor.capture());
    }
}