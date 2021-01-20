package info.pionas.rental.application.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class ApartmentApplicationServiceTest {
    private static final ApartmentRepository apartmentRepository = Mockito.mock(ApartmentRepository.class);
    private static final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private static final EventChannel eventChannel = Mockito.mock(EventChannel.class);
    private static final ApartmentApplicationService service = new ApartmentApplicationService(apartmentRepository, bookingRepository, eventChannel);
    private static final String APARTMENT_ID = "123";
    private static final String OWNER_ID = "123";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "123";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Room with jacuzzi";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    @Test
    void shouldCreateApartmentWithAllInformation() {
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        service.add(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);

        then(apartmentRepository).should().save(captor.capture());
    }


    @Test
    void shouldBookingApartment() {
        String tenantId = "456";
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate middle = start.plusDays(1);
        LocalDate end = start.plusDays(2);
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

        givenExistingApartment();
        service.book(APARTMENT_ID, tenantId, start, end);

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isApartment()
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(asList(start, middle, end))
                .hasBookingPeriodThatHas(start, end);
    }

    private void givenExistingApartment() {
        Apartment apartment = getApartment();
        given(apartmentRepository.findById(APARTMENT_ID)).willReturn(apartment);
    }

    private Apartment getApartment() {
        return new ApartmentFactory().create(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);
    }
}