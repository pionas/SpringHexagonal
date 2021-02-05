package info.pionas.rental.application.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.address.AddressCatalogue;
import info.pionas.rental.domain.address.AddressDto;
import info.pionas.rental.domain.address.AddressException;
import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.money.Money;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
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
    private static final LocalDate END = LocalDate.of(2040, 3, 6);
    private static final UUID BOOKING_ID = UUID.randomUUID();
    private static final String NO_ID = null;
    private static final LocalDate BEFORE_START = START.minusDays(1);
    private static final LocalDate AFTER_START = START.plusDays(1);
    private static final BigDecimal PRICE = BigDecimal.valueOf(42);
    private static final Money MONEY = Money.of(BigDecimal.valueOf(20));

    private final OwnerRepository ownerRepository = mock(OwnerRepository.class);
    private final TenantRepository tenantRepository = mock(TenantRepository.class);
    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final ApartmentOfferRepository apartmentOfferRepository = mock(ApartmentOfferRepository.class);
    private final AddressCatalogue addressCatalogue = mock(AddressCatalogue.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final ApartmentApplicationService service = new ApartmentApplicationServiceFactory()
            .apartmentApplicationService(apartmentRepository, bookingRepository, ownerRepository, tenantRepository,
                    apartmentOfferRepository, addressCatalogue, new FakeEventIdFactory(), new FakeClock(), eventChannel);

    @Test
    void shouldAddNewApartment() {
        givenOwnerExists();
        givenExistingAddress();
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);

        service.add(givenApartmentDto());

        then(apartmentRepository).should().save(captor.capture());
        ApartmentAssertion.assertThat(captor.getValue())
                .isEqualTo(ApartmentRequirements.apartment()
                        .withOwnerId(OWNER_ID)
                        .withApartmentNumber(APARTMENT_NUMBER)
                        .withAddress(STREET, POSTAL_CODE, HOUSE_NUMBER, CITY, COUNTRY)
                )
                .hasDescriptionEqualsTo(DESCRIPTION)
                .hasSpacesEqualsTo(SPACES_DEFINITION);
    }

    @Test
    void shouldReturnIdOfNewApartment() {
        givenOwnerExists();
        givenExistingAddress();
        given(apartmentRepository.save(any())).willReturn(APARTMENT_ID);

        String actual = service.add(givenApartmentDto());

        Assertions.assertThat(actual).isEqualTo(APARTMENT_ID);
    }

    private ApartmentDto givenApartmentDto() {
        return givenApartmentDtoWith(SPACES_DEFINITION);
    }

    @Test
    void shouldNotAllowToCreateApartmentWithAtLeastOneSpaceThatHaveSquareMeterEqualZero() {
        givenOwnerExists();
        givenExistingAddress();
        ApartmentDto apartmentDto = givenApartmentDtoWith(ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0, "Room", 0.0));

        SquareMeterException actual = assertThrows(SquareMeterException.class, () -> service.add(apartmentDto));

        assertThat(actual).hasMessage("Square meter lower or equal zero");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldNotAllowToCreateApartmentWithAtLeastOneSpaceThatHaveSquareMeterLowerThanZero() {
        givenOwnerExists();
        givenExistingAddress();
        ApartmentDto apartmentDto = givenApartmentDtoWith(ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0, "Room", -13.0));

        SquareMeterException actual = assertThrows(SquareMeterException.class, () -> service.add(apartmentDto));

        assertThat(actual).hasMessage("Square meter lower or equal zero");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldRecognizeOwnerDoesNotExist() {
        givenOwnerDoesNotExist();
        givenExistingAddress();

        OwnerDoesNotExistException actual = assertThrows(OwnerDoesNotExistException.class, () -> service.add(givenApartmentDto()));

        assertThat(actual).hasMessage("Owner with id " + OWNER_ID + " does not exist");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldRecognizeAddressDoesNotExist() {
        givenOwnerExists();
        givenNotExistingAddress();

        AddressException actual = assertThrows(AddressException.class, () -> service.add(givenApartmentDto()));

        assertThat(actual).hasMessage(
                "Address: street: " + STREET + ", postalCode: " + POSTAL_CODE + ", buildingNumber: " + HOUSE_NUMBER + ", city: " + CITY +
                        ", country: " + COUNTRY + "  does not exist.");
        then(apartmentRepository).should(never()).save(any());
    }

    @Test
    void shouldCreateBookingForApartment() {
        givenExistingTenantAndApartmentWithNoBookings();
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

        service.book(givenBookApartmentDto());

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isEqualToBookingApartment(NO_ID, TENANT_ID, OWNER_ID, Money.of(PRICE), new Period(START, END));
    }

    @Test
    void shouldAllowToBookApartmentWhenFoundAcceptedBookingsInDifferentPeriod() {
        givenExistingApartment();
        givenExistingTenant();
        givenAcceptedBookingsInDifferentPeriod();
        givenExistingApartmentOffer();
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

        service.book(givenBookApartmentDto());

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isEqualToBookingApartment(NO_ID, TENANT_ID, OWNER_ID, Money.of(PRICE), new Period(START, END));
    }


    @Test
    void shouldReturnIdOfBooking() {
        givenExistingTenantAndApartmentWithNoBookings();
        given(bookingRepository.save(any())).willReturn(BOOKING_ID);

        UUID actual = service.book(givenBookApartmentDto());

        Assertions.assertThat(actual).isEqualTo(BOOKING_ID);
    }

    @Test
    void shouldPublishApartmentBookedEvent() {
        givenExistingTenantAndApartmentWithNoBookings();
        ArgumentCaptor<ApartmentBooked> captor = ArgumentCaptor.forClass(ApartmentBooked.class);

        service.book(givenBookApartmentDto());

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
    void shouldRecognizeApartmentDoesNotExistWhenBooking() {
        givenNonExistingApartment();
        givenExistingTenant();
        givenNoBookings();

        ApartmentNotFoundException actual = assertThrows(ApartmentNotFoundException.class, () -> service.book(givenBookApartmentDto()));

        assertThat(actual).hasMessage("Apartment with id " + APARTMENT_ID + " does not exist");
        thenBookingWasNotCreated();
    }


    @Test
    void shouldRecognizeTenantDoesNotExistWhenBooking() {
        givenExistingApartment();
        givenNonExistingTenant();
        givenNoBookings();

        TenantNotFoundException actual = assertThrows(TenantNotFoundException.class, () -> service.book(givenBookApartmentDto()));

        assertThat(actual).hasMessage("Tenant with id: " + TENANT_ID + " does not exist");
        thenBookingWasNotCreated();
    }

    private void givenNonExistingTenant() {
        given(tenantRepository.existById(TENANT_ID)).willReturn(false);
    }

    @Test
    void shouldRecognizeWhenHaveBookingsWithinGivenPeriodWhenBooking() {
        givenExistingApartment();
        givenExistingTenant();
        givenAcceptedBookingsInGivenPeriod();
        givenExistingApartmentOffer();

        ApartmentBookingException actual = assertThrows(ApartmentBookingException.class, () -> service.book(givenBookApartmentDto()));

        assertThat(actual).hasMessage("There are accepted bookings in given period");
        thenBookingWasNotCreated();
    }


    @Test
    void shouldRecognizeWhenStartDateIsFromPastWhenBooking() {
        givenExistingTenantAndApartmentWithNoBookings();
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, LocalDate.of(2020, 10, 10), END);

        PeriodException actual = assertThrows(PeriodException.class, () -> service.book(apartmentBookingDto));

        assertThat(actual).hasMessage("Start date: 2020-10-10 is past date");
        thenBookingWasNotCreated();
    }

    @Test
    void shouldRecognizeWhenEndDateIsBeforeStartDateWhenBooking() {
        givenExistingTenantAndApartmentWithNoBookings();
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, END, START);

        PeriodException actual = assertThrows(PeriodException.class, () -> service.book(apartmentBookingDto));

        assertThat(actual).hasMessage("Start date: 2040-03-06 of period is after end date: 2040-03-04");
        thenBookingWasNotCreated();
    }

    private ApartmentDto givenApartmentDtoWith(Map<String, Double> spacesDefinition) {
        return new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, spacesDefinition);
    }

    private void givenOwnerExists() {
        given(ownerRepository.exists(OWNER_ID)).willReturn(true);
    }

    private void givenExistingAddress() {
        given(addressCatalogue.exists(addressDto())).willReturn(true);
    }

    private AddressDto addressDto() {
        return new AddressDto(STREET, POSTAL_CODE, HOUSE_NUMBER, CITY, COUNTRY);
    }

    private void givenNotExistingAddress() {
        given(addressCatalogue.exists(addressDto())).willReturn(false);
    }

    private void givenOwnerDoesNotExist() {
        given(ownerRepository.exists(OWNER_ID)).willReturn(false);
    }

    private void givenAcceptedBookingsInDifferentPeriod() {
        givenAcceptedBookingItPeriod(BEFORE_START.minusDays(10), BEFORE_START);
    }

    private void givenNonExistingApartment() {
        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(false);
    }

    private void givenAcceptedBookingsInGivenPeriod() {
        givenAcceptedBookingItPeriod(BEFORE_START, AFTER_START);
    }

    private void givenAcceptedBookingItPeriod(LocalDate periodStart, LocalDate periodEnd) {
        Booking acceptedBooking = Booking.apartment(APARTMENT_ID, TENANT_ID, OWNER_ID, MONEY, new Period(periodStart, periodEnd));
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(asList(acceptedBooking));
    }

    private void givenExistingTenantAndApartmentWithNoBookings() {
        givenExistingApartment();
        givenExistingTenant();
        givenNoBookings();
        givenExistingApartmentOffer();
    }

    private void givenExistingApartmentOffer() {
        given(apartmentOfferRepository.existByApartmentId(APARTMENT_ID)).willReturn(true);
        ApartmentOffer apartmentOffer = ApartmentOffer.Builder.apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(PRICE)
                .withAvailability(BEFORE_START, END.plusDays(10)).build();
        given(apartmentOfferRepository.findByApartmentId(APARTMENT_ID)).willReturn(apartmentOffer);
    }

    private void givenNoBookings() {
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(emptyList());
    }

    private RentalPlaceIdentifier getRentalPlaceIdentifier() {
        return RentalPlaceIdentifierFactory.apartment(NO_ID);
    }

    private void thenBookingWasNotCreated() {
        then(bookingRepository).should(never()).save(any());
        then(eventChannel).should(never()).publish(any(ApartmentBooked.class));
    }

    private ApartmentBookingDto givenBookApartmentDto() {
        return new ApartmentBookingDto(APARTMENT_ID, TENANT_ID, START, END);
    }

    private void givenExistingTenant() {
        given(tenantRepository.existById(TENANT_ID)).willReturn(true);
    }

    private void givenExistingApartment() {
        Apartment apartment = apartment()
                .withOwnerId(OWNER_ID)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withHouseNumber(HOUSE_NUMBER)
                .withApartmentNumber(APARTMENT_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .withDescription(DESCRIPTION)
                .withSpacesDefinition(SPACES_DEFINITION)
                .build();

        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(true);
        given(apartmentRepository.findById(APARTMENT_ID)).willReturn(apartment);
    }
}