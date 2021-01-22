package info.pionas.rental.application.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static info.pionas.rental.domain.hotel.HotelAssertion.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class HotelApplicationServiceTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";

    private final HotelRepository repository = mock(HotelRepository.class);
    private final HotelApplicationService service = new HotelApplicationService(repository);

    @Test
    void shouldCreateHotel() {
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);

        service.add(NAME, STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);

        then(repository).should().save(captor.capture());
        assertThat(captor.getValue())
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }
}