package info.pionas.rental.application.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ApartmentApplicationServiceTest {
    private static final String APARTMENT_ID = "2178231";
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final String TENANT_ID = "137";
    private static final LocalDate START = LocalDate.of(2020, 3, 4);
    private static final LocalDate MIDDLE = LocalDate.of(2020, 3, 5);
    private static final LocalDate END = LocalDate.of(2020, 3, 6);
    private static final String BOOKING_ID = "8394234";

    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final ApartmentApplicationService service = new ApartmentApplicationService(apartmentRepository, bookingRepository, eventChannel);
    private final ApartmentFactory apartmentFactory = new ApartmentFactory();

    @Test
    void shouldAddNewApartment() {
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);

        ApartmentDto apartmentDto = givenApartmentDto();
        service.add(apartmentDto);

        then(apartmentRepository).should().save(captor.capture());
        ApartmentAssertion.assertThat(captor.getValue())
                .hasOwnerIdEqualsTo(OWNER_ID)
                .hasDescriptionEqualsTo(DESCRIPTION)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY)
                .hasRoomsEqualsTo(ROOMS_DEFINITION);
    }

    @Test
    void shouldReturnIdOfNewApartment() {
        given(apartmentRepository.save(any())).willReturn(APARTMENT_ID);

        ApartmentDto apartmentDto = givenApartmentDto();
        String actual = service.add(apartmentDto);

        Assertions.assertThat(actual).isEqualTo(APARTMENT_ID);
    }

    @Test
    void shouldCreateBookingForApartment() {
        givenApartment();
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

        service.book(APARTMENT_ID, TENANT_ID, START, END);

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isApartment()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(START, MIDDLE, END);
    }

    @Test
    void shouldReturnIdOfBooking() {
        givenApartment();
        given(bookingRepository.save(any())).willReturn(BOOKING_ID);

        String actual = service.book(APARTMENT_ID, TENANT_ID, START, END);

        Assertions.assertThat(actual).isEqualTo(BOOKING_ID);
    }

    private void givenApartment() {
        Apartment apartment = apartmentFactory.create(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);
        given(apartmentRepository.findById(APARTMENT_ID)).willReturn(apartment);
    }

    private ApartmentDto givenApartmentDto() {
        return new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);
    }

}