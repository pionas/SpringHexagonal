package info.pionas.rental.application.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.owner.OwnerDoesNotExistException;
import info.pionas.rental.domain.owner.OwnerRepository;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

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
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final String TENANT_ID = "137";
    private static final LocalDate START = LocalDate.of(2020, 3, 4);
    private static final LocalDate MIDDLE = LocalDate.of(2020, 3, 5);
    private static final LocalDate END = LocalDate.of(2020, 3, 6);
    private static final String BOOKING_ID = "8394234";

    private final OwnerRepository ownerRepository = mock(OwnerRepository.class);
    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final ApartmentApplicationService service = new ApartmentApplicationServiceFactory().apartmentApplicationService(apartmentRepository, bookingRepository, ownerRepository, new FakeEventIdFactory(), new FakeClock(), eventChannel);
    private final ApartmentFactory apartmentFactory = new ApartmentFactory(ownerRepository);

    @Test
    void shouldAddNewApartment() {
        givenOwnerExist();
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);

        ApartmentDto apartmentDto = givenApartmentDto();
        service.add(apartmentDto);

        then(apartmentRepository).should().save(captor.capture());
        ApartmentAssertion.assertThat(captor.getValue())
                .hasOwnerIdEqualsTo(OWNER_ID)
                .hasDescriptionEqualsTo(DESCRIPTION)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY)
                .hasSpacesEqualsTo(SPACES_DEFINITION);
    }

    @Test
    void shouldRecognizeOwnerDoesNotExist() {
        givenOwnerDoesNotExist();

        OwnerDoesNotExistException actual = assertThrows(OwnerDoesNotExistException.class, () -> service.add(givenApartmentDto()));
        assertThat(actual).hasMessage("Owner with id " + OWNER_ID + " does not exist");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldReturnIdOfNewApartment() {
        givenOwnerExist();
        given(apartmentRepository.save(any())).willReturn(APARTMENT_ID);

        ApartmentDto apartmentDto = givenApartmentDto();
        String actual = service.add(apartmentDto);

        Assertions.assertThat(actual).isEqualTo(APARTMENT_ID);
    }

    @Test
    void shouldCreateBookingForApartment() {
        givenOwnerExist();
        givenApartment();
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

        service.book(getApartmentBookingDto());

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isApartment()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(START, MIDDLE, END);
    }

    @Test
    void shouldReturnIdOfBooking() {
        givenOwnerExist();
        givenApartment();
        ArgumentCaptor<ApartmentBooked> captor = ArgumentCaptor.forClass(ApartmentBooked.class);

        service.book(getApartmentBookingDto());

        then(eventChannel).should().publish(captor.capture());
        ApartmentBooked actual = captor.getValue();
        assertThat(actual.getEventId()).isEqualTo(FakeEventIdFactory.UUID);
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getOwnerId()).isEqualTo(OWNER_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getPeriodStart()).isEqualTo(START);
        assertThat(actual.getPeriodEnd()).isEqualTo(END);
    }

    @Test
    void shouldPublishApartmentBookedEvent() {
        givenOwnerExist();
        givenApartment();
        given(bookingRepository.save(any())).willReturn(BOOKING_ID);

        String actual = service.book(getApartmentBookingDto());

        Assertions.assertThat(actual).isEqualTo(BOOKING_ID);
    }

    private void givenOwnerDoesNotExist() {
        given(ownerRepository.exists(OWNER_ID)).willReturn(false);
    }

    private void givenOwnerExist() {
        given(ownerRepository.exists(OWNER_ID)).willReturn(true);
    }

    private ApartmentBookingDto getApartmentBookingDto() {
        return new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, START, END);
    }

    private void givenApartment() {
        ApartmentDto apartmentDto = new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, SPACES_DEFINITION);
        Apartment apartment = apartmentFactory.create(apartmentDto.asNewApartmentDto());
        given(apartmentRepository.findById(APARTMENT_ID)).willReturn(apartment);
    }

    private ApartmentDto givenApartmentDto() {
        return new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, SPACES_DEFINITION);
    }

}