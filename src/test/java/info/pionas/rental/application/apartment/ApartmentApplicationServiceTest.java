package info.pionas.rental.application.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAccepted;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.owner.OwnerDoesNotExistException;
import info.pionas.rental.domain.owner.OwnerRepository;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.period.PeriodException;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifierFactory;
import info.pionas.rental.domain.space.SquareMeterException;
import info.pionas.rental.domain.tenant.TenantNotFoundException;
import info.pionas.rental.domain.tenant.TenantRepository;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    private static final LocalDate START = LocalDate.of(2040, 3, 4);
    private static final LocalDate MIDDLE = LocalDate.of(2040, 3, 5);
    private static final LocalDate END = LocalDate.of(2040, 3, 6);
    private static final String BOOKING_ID = "8394234";
    private static final LocalDate BEFORE_START = START.minusDays(1);
    private static final LocalDate AFTER_START = START.plusDays(1);

    private final OwnerRepository ownerRepository = mock(OwnerRepository.class);
    private final TenantRepository tenantRepository = mock(TenantRepository.class);
    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final ApartmentApplicationService service = new ApartmentApplicationServiceFactory()
            .apartmentApplicationService(apartmentRepository, bookingRepository, ownerRepository, tenantRepository, new FakeEventIdFactory(), new FakeClock(), eventChannel);
    private final ApartmentFactory apartmentFactory = new ApartmentFactory(ownerRepository);

    @Test
    void shouldAddNewApartment() {
        givenExistingOwner();
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
        givenExistingOwner();
        given(apartmentRepository.save(any())).willReturn(APARTMENT_ID);

        ApartmentDto apartmentDto = givenApartmentDto();
        String actual = service.add(apartmentDto);

        Assertions.assertThat(actual).isEqualTo(APARTMENT_ID);
    }

    @Test
    void shouldNotAllowToCreateApartmentWithAtLeastOneSpaceThatHaveSquareMaterEqualZero() {
        givenExistingOwner();
        ApartmentDto apartmentDto = getApartmentDtoWith(ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0, "Room", 0.0));
        SquareMeterException actual = assertThrows(SquareMeterException.class, () -> service.add(apartmentDto));
        assertThat(actual).hasMessage("Square meter lower or equal zero");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldNotAllowToCreateApartmentWithAtLeastOneSpaceThatHaveSquareMaterLowerThanZero() {
        givenExistingOwner();
        ApartmentDto apartmentDto = getApartmentDtoWith(ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0, "Room", -13.0));
        SquareMeterException actual = assertThrows(SquareMeterException.class, () -> service.add(apartmentDto));
        assertThat(actual).hasMessage("Square meter lower or equal zero");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldCreateBookingForApartment() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

        service.book(getApartmentBookingDto());

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isApartment()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(START, MIDDLE, END);
    }

    @Test
    void shouldAllowToBookApartmentWhenFoundAcceptedBookingsInDifferentPeriod() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        givenAcceptedBookingsInDifferentPeriod();

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
    void shouldAllowToBookApartmentWheBookingsIsEmpty() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(null);

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
    void shouldReturnIdOfBooking() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
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
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        given(bookingRepository.save(any())).willReturn(BOOKING_ID);

        String actual = service.book(getApartmentBookingDto());

        Assertions.assertThat(actual).isEqualTo(BOOKING_ID);
    }

    @Test
    void shouldRecognizeApartmentDoesNotExistWhenBooking() {
        givenExistingOwner();
        givenNonExistingApartment();

        ApartmentNotFoundException actual = assertThrows(ApartmentNotFoundException.class, () -> {
            service.book(getApartmentBookingDto());
        });

        assertThat(actual).hasMessage("Apartment with id " + APARTMENT_ID + " does not exist");
        thenBookingWasNotCreated();
    }

    @Test
    void shouldRecognizeTenantDoesNotExistWhenBooking() {
        givenExistingOwner();
        givenExistingApartment();
        givenNonExistingTenant();
        givenNoBookings();

        TenantNotFoundException actual = assertThrows(TenantNotFoundException.class, () -> {
            service.book(getApartmentBookingDto());
        });

        assertThat(actual).hasMessage("Tenant with id " + TENANT_ID + " does not exist");
        thenBookingWasNotCreated();
    }

    @Test
    void shouldRecognizeWhenHaveBookingsWithinGivenPeriodWhenBooking() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        givenAcceptedBookingsInGivenPeriod();

        ApartmentBookingException actual = assertThrows(ApartmentBookingException.class, () -> {
            service.book(getApartmentBookingDto());
        });

        assertThat(actual).hasMessage("There are accepted bookings in given period");
        thenBookingWasNotCreated();
    }

    @Test
    void shouldRecognizeWhenStartDateIsFromPastWhenBooking() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        givenAcceptedBookingsInGivenPeriod();
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, LocalDate.of(2020, 10, 10), END);
        PeriodException actual = assertThrows(PeriodException.class, () -> {
            service.book(apartmentBookingDto);
        });

        assertThat(actual).hasMessage("Start date: 2020-10-10 is past date");
        thenBookingWasNotCreated();
    }

    @Test
    void shouldRecognizeWhenEndDateIsBeforeStartDateWhenBooking() {
        givenExistingOwner();
        givenExistingTenantAndApartmentWithNoBookings();
        givenAcceptedBookingsInGivenPeriod();
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, END, START);
        PeriodException actual = assertThrows(PeriodException.class, () -> {
            service.book(apartmentBookingDto);
        });

        assertThat(actual).hasMessage("Start date: 2040-03-06 of availability is after end date: 2040-03-04");
        thenBookingWasNotCreated();
    }

    private void givenAcceptedBookingsInGivenPeriod() {
        givenAcceptedBookingItPeriod(BEFORE_START, AFTER_START);
    }

    private void givenAcceptedBookingsInDifferentPeriod() {
        givenAcceptedBookingItPeriod(BEFORE_START.minusDays(10), BEFORE_START);
    }

    private void givenAcceptedBookingItPeriod(LocalDate beforeStart, LocalDate afterStart) {
        Booking acceptedBooking = Booking.apartment(APARTMENT_ID, TENANT_ID, new Period(beforeStart, afterStart));
        List<Booking> bookings = asList(acceptedBooking);
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(bookings);
    }

    private void thenBookingWasNotCreated() {
        then(bookingRepository).should(never()).save(any());
        then(eventChannel).should(never()).publish(any(BookingAccepted.class));
    }

    private void givenOwnerDoesNotExist() {
        given(ownerRepository.exists(OWNER_ID)).willReturn(false);
    }

    private void givenExistingOwner() {
        given(ownerRepository.exists(OWNER_ID)).willReturn(true);
    }

    private ApartmentBookingDto getApartmentBookingDto() {
        return new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, START, END);
    }

    private void givenExistingTenantAndApartmentWithNoBookings() {
        givenExistingApartment();
        givenExistingTenant();
        givenNoBookings();
    }

    private void givenNoBookings() {
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(emptyList());
    }

    private RentalPlaceIdentifier getRentalPlaceIdentifier() {
        return RentalPlaceIdentifierFactory.apartment(null);
    }

    private void givenExistingApartment() {
        ApartmentDto apartmentDto = getApartmentDtoWith(SPACES_DEFINITION);
        Apartment apartment = apartmentFactory.create(apartmentDto.asNewApartmentDto());
        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(true);
        given(apartmentRepository.findById(APARTMENT_ID)).willReturn(apartment);
    }

    private void givenNonExistingApartment() {
        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(false);
        given(apartmentRepository.findById(APARTMENT_ID)).willThrow(new ApartmentNotFoundException(APARTMENT_ID));
    }


    private void givenExistingTenant() {
        given(tenantRepository.existById(TENANT_ID)).willReturn(true);
    }

    private void givenNonExistingTenant() {
        given(tenantRepository.existById(TENANT_ID)).willReturn(false);
    }

    private ApartmentDto givenApartmentDto() {
        return getApartmentDtoWith(SPACES_DEFINITION);
    }

    private ApartmentDto getApartmentDtoWith(Map<String, Double> spacesDefinition) {
        return new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, spacesDefinition);
    }

}