package info.pionas.rental.domain.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferNotFoundException;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.period.PeriodException;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifierFactory;
import info.pionas.rental.domain.tenant.TenantNotFoundException;
import info.pionas.rental.domain.tenant.TenantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class ApartmentDomainServiceTest {
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
    private static final String NO_ID = null;
    private static final LocalDate BEFORE_START = START.minusDays(1);
    private static final LocalDate AFTER_START = START.plusDays(1);
    private static final BigDecimal PRICE = BigDecimal.valueOf(42);
    private static final Money PRICE_AS_MONEY = Money.of(PRICE);
    private static final LocalDate START_AVAILABILITY = START.minusDays(10);
    private static final LocalDate END_AVAILABILITY = END.plusDays(20);

    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final ApartmentOfferRepository apartmentOfferRepository = mock(ApartmentOfferRepository.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final TenantRepository tenantRepository = mock(TenantRepository.class);
    private final ApartmentEventsPublisher apartmentEventsPublisher = mock(ApartmentEventsPublisher.class);

    private final ApartmentDomainService service = new ApartmentDomainService(
            apartmentRepository, tenantRepository, bookingRepository, apartmentOfferRepository, apartmentEventsPublisher);

    @Test
    void shouldCreateBookingForApartment() {
        givenExistingTenantAndApartmentWithOfferAndWithoutBookings();

        Booking actual = service.book(givenNewApartmentBookingDto());

        BookingAssertion.assertThat(actual)
                .isEqualToBookingApartment(NO_ID, TENANT_ID, OWNER_ID, PRICE_AS_MONEY, new Period(START, END));
    }

    @Test
    void shouldAllowToBookApartmentWhenFoundAcceptedBookingsInDifferentPeriod() {
        givenExistingApartment();
        givenExistingTenant();
        givenExistingApartmentOffer();
        givenAcceptedBookingsInDifferentPeriod();

        Booking actual = service.book(givenNewApartmentBookingDto());

        BookingAssertion.assertThat(actual)
                .isEqualToBookingApartment(NO_ID, TENANT_ID, OWNER_ID, PRICE_AS_MONEY, new Period(START, END));
    }

    private void givenAcceptedBookingsInDifferentPeriod() {
        givenAcceptedBookingItPeriod(BEFORE_START.minusDays(10), BEFORE_START);
    }

    @Test
    void shouldPublishApartmentBookedEvent() {
        givenExistingTenantAndApartmentWithOfferAndWithoutBookings();

        service.book(givenNewApartmentBookingDto());

        then(apartmentEventsPublisher).should().publishApartmentBooked(NO_ID, OWNER_ID, TENANT_ID, new Period(START, END));
    }

    @Test
    void shouldRecognizeApartmentDoesNotExistWhenBooking() {
        givenNonExistingApartment();
        givenExistingTenant();
        givenNoBookings();
        givenExistingApartmentOffer();

        ApartmentNotFoundException actual = assertThrows(ApartmentNotFoundException.class, () -> service.book(givenNewApartmentBookingDto()));

        assertThat(actual).hasMessage("Apartment with id " + APARTMENT_ID + " does not exist");
        thenApartmentWasNotBooked();
    }

    private void givenNonExistingApartment() {
        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(false);
    }

    @Test
    void shouldRecognizeTenantDoesNotExistWhenBooking() {
        givenExistingApartment();
        givenNonExistingTenant();
        givenNoBookings();
        givenExistingApartmentOffer();

        TenantNotFoundException actual = assertThrows(TenantNotFoundException.class, () -> service.book(givenNewApartmentBookingDto()));

        assertThat(actual).hasMessage("Tenant with id: " + TENANT_ID + " does not exist");
        thenApartmentWasNotBooked();
    }

    private void givenNonExistingTenant() {
        given(tenantRepository.existById(TENANT_ID)).willReturn(false);
    }

    @Test
    void shouldRecognizeWhenHaveBookingsWithinGivenPeriodWhenBooking() {
        givenExistingApartment();
        givenExistingApartmentOffer();
        givenExistingTenant();
        givenAcceptedBookingsInGivenPeriod();

        ApartmentBookingException actual = assertThrows(ApartmentBookingException.class, () -> service.book(givenNewApartmentBookingDto()));

        assertThat(actual).hasMessage("There are accepted bookings in given period");
        thenApartmentWasNotBooked();
    }

    private void givenAcceptedBookingsInGivenPeriod() {
        givenAcceptedBookingItPeriod(BEFORE_START, AFTER_START);
    }

    private void givenAcceptedBookingItPeriod(LocalDate periodStart, LocalDate periodEnd) {
        Booking acceptedBooking = Booking.apartment(APARTMENT_ID, TENANT_ID, new Period(periodStart, periodEnd));
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(asList(acceptedBooking));
    }

    @Test
    void shouldRecognizeWhenStartDateIsFromPastWhenBooking() {
        givenExistingTenantAndApartmentWithOfferAndWithoutBookings();
        NewApartmentBookingDto dto = new NewApartmentBookingDto(APARTMENT_ID, TENANT_ID, LocalDate.of(2020, 10, 10), END);

        PeriodException actual = assertThrows(PeriodException.class, () -> service.book(dto));

        assertThat(actual).hasMessage("Start date: 2020-10-10 is past date");
        thenApartmentWasNotBooked();
    }

    @Test
    void shouldRecognizeWhenEndDateIsBeforeStartDateWhenBooking() {
        givenExistingTenantAndApartmentWithOfferAndWithoutBookings();
        NewApartmentBookingDto dto = new NewApartmentBookingDto(APARTMENT_ID, TENANT_ID, END, START);

        PeriodException actual = assertThrows(PeriodException.class, () -> service.book(dto));

        assertThat(actual).hasMessage("Start date: 2040-03-06 of period is after end date: 2040-03-04");
        thenApartmentWasNotBooked();
    }

    private void givenExistingTenantAndApartmentWithOfferAndWithoutBookings() {
        givenExistingApartment();
        givenExistingTenant();
        givenNoBookings();
        givenExistingApartmentOffer();
    }

    @Test
    void shouldRecognizeWhenApartmentOfferDoesNotExist() {
        givenExistingApartment();
        givenExistingTenant();
        givenNoBookings();
        givenNotExistingApartmentOffer();

        ApartmentOfferNotFoundException actual = assertThrows(ApartmentOfferNotFoundException.class, () -> service.book(givenNewApartmentBookingDto()));

        assertThat(actual).hasMessage("Offer for apartment with id: " + APARTMENT_ID + " does not exist");
        thenApartmentWasNotBooked();
    }

    @ParameterizedTest
    @MethodSource("apartmentOfferAvailabilities")
    void shouldRecognizeWhenApartmentOfferIsNotWithingBookingPeriod(LocalDate start, LocalDate end) {
        givenExistingApartment();
        givenExistingTenant();
        givenNoBookings();
        givenExisting(givenApartmentOffer().withAvailability(start, end));

        AparmentBookingException actual = assertThrows(AparmentBookingException.class, () -> service.book(givenNewApartmentBookingDto()));

        assertThat(actual).hasMessage("Apartment is not available between 2040-03-04 - 2040-03-06");
        thenApartmentWasNotBooked();
    }

    private static Stream<Arguments> apartmentOfferAvailabilities() {
        return Stream.of(
                Arguments.of(START.plusDays(1), END.plusDays(10)),
                Arguments.of(START.minusDays(10), END.minusDays(1))
        );
    }

    private void givenNotExistingApartmentOffer() {
        given(apartmentOfferRepository.existByApartmentId(APARTMENT_ID)).willReturn(false);
    }

    private void givenExistingApartmentOffer() {
        givenExisting(givenApartmentOffer());
    }

    private void givenExisting(ApartmentOffer.Builder availability) {
        given(apartmentOfferRepository.existByApartmentId(APARTMENT_ID)).willReturn(true);
        ApartmentOffer apartmentOffer = availability.build();
        given(apartmentOfferRepository.findByApartmentId(APARTMENT_ID)).willReturn(apartmentOffer);
    }

    private ApartmentOffer.Builder givenApartmentOffer() {
        return ApartmentOffer.Builder.apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(PRICE)
                .withAvailability(START_AVAILABILITY, END_AVAILABILITY);
    }

    private void givenNoBookings() {
        given(bookingRepository.findAllAcceptedBy(getRentalPlaceIdentifier())).willReturn(emptyList());
    }

    private RentalPlaceIdentifier getRentalPlaceIdentifier() {
        return RentalPlaceIdentifierFactory.apartment(NO_ID);
    }

    private void thenApartmentWasNotBooked() {
        then(apartmentEventsPublisher).should(never()).publishApartmentBooked(anyString(), anyString(), anyString(), any(Period.class));
    }

    private NewApartmentBookingDto givenNewApartmentBookingDto() {
        return new NewApartmentBookingDto(APARTMENT_ID, TENANT_ID, START, END);
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