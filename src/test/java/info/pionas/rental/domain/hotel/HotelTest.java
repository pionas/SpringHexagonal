package info.pionas.rental.domain.hotel;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Period;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Map;

class HotelTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";
    private final HotelFactory hotelFactory = new HotelFactory();

    @Test
    void shouldCreateHotelWithAllRequiredFields() {
        Hotel actual = createHotel();

        HotelAssertion.assertThat(actual)
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    private Hotel createHotel() {
        return hotelFactory.create(NAME, STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }
}