package info.pionas.rental.application.booking;

import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.agreement.AgreementAssertion;
import info.pionas.rental.domain.agreement.AgreementRepository;
import info.pionas.rental.domain.booking.*;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifierFactory;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static info.pionas.rental.domain.rentalplaceidentifier.RentalType.APARTMENT;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class BookingCommandHandlerTest {
    private static final String BOOKING_ID = "74398";
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID = "5678";
    private static final String OWNER_ID = "123";
    public static final LocalDate TODAY = LocalDate.now();
    private static final Period PERIOD = Period.from(TODAY, TODAY.plusDays(1));
    private static final Money PRICE = Money.of(BigDecimal.valueOf(42));
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));

    private final ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final AgreementRepository agreementRepository = mock(AgreementRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final FakeEventIdFactory eventIdFactory = new FakeEventIdFactory();
    private final FakeClock clock = new FakeClock();
    private final BookingCommandHandler commandHandler = new BookingCommandHandlerFactory().bookingCommandHandler(
            bookingRepository, agreementRepository, eventIdFactory, clock, eventChannel);

    @Test
    void shouldCreateAgreementWhenBookingAccepted() {
        ArgumentCaptor<Agreement> captor = ArgumentCaptor.forClass(Agreement.class);
        givenBookingsWithoutCollision();
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(agreementRepository).should().save(captor.capture());
        AgreementAssertion.assertThat(captor.getValue())
                .isEqualToAgreement(APARTMENT, RENTAL_PLACE_ID, OWNER_ID, TENANT_ID, DAYS, PRICE);
    }

    @Test
    void shouldAcceptBookingWhenBookingsWithCollisionNotFound() {
        givenBookingsWithoutCollision();
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedOnceAccepted() {
        givenBookingsWithoutCollision();
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        assertThat(actual.getRentalType()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    private void givenBookingsWithoutCollision() {
        givenBookings(asList(
                Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, OWNER_ID, PRICE, PERIOD),
                Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, OWNER_ID, PRICE, PERIOD)));
    }

    @Test
    void shouldRejectBookingWhenBookingsWithCollisionFound() {
        givenBookingsWithCollision();
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isRejected();
    }

    @Test
    void shouldNotCreateAgreementWhenBookingRejectedDurningAcceptance() {
        givenBookingsWithCollision();
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(agreementRepository).should(never()).save(any());
    }

    private void givenBookingsWithCollision() {
        Booking booking = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, OWNER_ID, PRICE, PERIOD);
        booking.accept(new BookingEventsPublisher(eventIdFactory, clock, eventChannel));
        givenBookings(asList(booking, Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, OWNER_ID, PRICE, PERIOD)));
    }

    private void givenBookings(List<Booking> bookings) {
        RentalPlaceIdentifier identifier = RentalPlaceIdentifierFactory.apartment(RENTAL_PLACE_ID);
        given(bookingRepository.findAllBy(identifier)).willReturn(bookings);
    }

    @Test
    void shouldRejectBooking() {
        givenOpenBooking();

        commandHandler.reject(new BookingReject(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isRejected();
    }

    private void givenOpenBooking() {
        Booking booking = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, OWNER_ID, PRICE, PERIOD);
        given(bookingRepository.findById(BOOKING_ID)).willReturn(booking);
    }
}