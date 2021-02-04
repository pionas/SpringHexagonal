package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingDomainServiceTest {
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID_1 = "5678";
    private static final String TENANT_ID_2 = "123456";
    private static final String OWNER_ID = "123";
    public static final LocalDate TODAY = LocalDate.now();
    private static final List<LocalDate> DAYS = asList(TODAY, TODAY.plusDays(1));
    private static final Period PERIOD = Period.from(TODAY, TODAY.plusDays(1));
    private static final Period PERIOD_WITH_COLLISION = Period.from(TODAY, TODAY.plusDays(10));
    private static final Period PERIOD_WITHOUT_COLLISION = Period.from(TODAY.plusDays(10), TODAY.plusDays(20));
    private static final List<Booking> NO_BOOKINGS_FOUND = emptyList();
    private static final Money PRICE = Money.of(BigDecimal.valueOf(42));

    private final EventIdFactory eventIdFactory = mock(EventIdFactory.class);
    private final Clock clock = mock(Clock.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingDomainService service = new BookingDomainServiceFactory().create(eventIdFactory, clock, eventChannel);
    private final BookingEventsPublisher bookingEventsPublisher = new BookingEventsPublisher(eventIdFactory, clock, eventChannel);

    @Test
    void shouldAcceptBookingWhenNoOtherBookingsFound() {
        Booking booking = givenApartmentBooking();

        Optional<Agreement> actual = service.accept(booking, emptyList());

        BookingAssertion.assertThat(booking).isAccepted();
//        AgreementAssertion.assertThat(actual)
//                .isEqualToAgreement(RENTAL_PLACE_ID, TENANT_ID_1, OWNER_ID_1, DAYS, PRICE);
    }

    @Test
    void shouldPublishBookingAcceptedEventWhenBookingIsAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);

        service.accept(givenApartmentBooking(), NO_BOOKINGS_FOUND);

        then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        assertThat(actual.getRentalType()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID_1);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    @Test
    void shouldRejectBookingWhenOtherWithDaysCollisionFound() {
        Booking booking = givenApartmentBooking();

        service.accept(booking, asList(givenAcceptedBookingWithDaysCollision()));

        BookingAssertion.assertThat(booking).isRejected();
    }

    @Test
    void shouldPublishBookingRejectedEventWhenBookingIsRejected() {
        ArgumentCaptor<BookingRejected> captor = ArgumentCaptor.forClass(BookingRejected.class);
        Booking booking = givenApartmentBooking();

        service.accept(booking, asList(givenAcceptedBookingWithDaysCollision()));

        then(eventChannel).should().publish(captor.capture());
        BookingRejected actual = captor.getValue();
        assertThat(actual.getRentalType()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID_1);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    @Test
    void shouldAcceptBookingWhenOtherWithoutDaysCollisionFound() {
        Booking booking = givenApartmentBooking();

        service.accept(booking, asList(givenAcceptedBookingWithoutDaysCollision()));

        BookingAssertion.assertThat(booking).isAccepted();
    }

    @Test
    void shouldAcceptBookingWhenOtherWithDaysCollisionButNotAcceptedFound() {
        Booking booking = givenApartmentBooking();

        service.accept(booking, asList(givenOpenBookingWithDaysCollision()));

        BookingAssertion.assertThat(booking).isAccepted();
    }


    @Test
    void shouldAcceptBookingWhenOthersWithoutCollisionFound() {
        Booking booking = givenApartmentBooking();
        List<Booking> bookings = asList(
                givenOpenBookingWithDaysCollision(),
                givenRejectedBookingWithDaysCollision(),
                givenAcceptedBookingWithoutDaysCollision()
        );

        service.accept(booking, bookings);

        BookingAssertion.assertThat(booking).isAccepted();
    }

    @Test
    void shouldAcceptBookingWhenFoundOnlyItself() {
        Booking booking = givenApartmentBooking();

        service.accept(booking, asList(booking));

        BookingAssertion.assertThat(booking).isAccepted();
    }

    @Test
    void shouldRejectBookingWhenAtLeastOneWithCollisionFound() {
        Booking booking = givenApartmentBooking();
        List<Booking> bookings = asList(
                givenOpenBookingWithDaysCollision(), givenRejectedBookingWithDaysCollision(),
                givenAcceptedBookingWithoutDaysCollision(), givenAcceptedBookingWithDaysCollision());

        service.accept(booking, bookings);

        BookingAssertion.assertThat(booking).isRejected();
    }

    private Booking givenRejectedBookingWithDaysCollision() {
        Booking booking = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID_2, OWNER_ID, PRICE, PERIOD_WITHOUT_COLLISION);
        booking.reject(bookingEventsPublisher);

        return booking;
    }

    private Booking givenAcceptedBookingWithoutDaysCollision() {
        Booking booking = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID_2, OWNER_ID, PRICE, PERIOD_WITHOUT_COLLISION);
        booking.accept(bookingEventsPublisher);

        return booking;
    }

    private Booking givenAcceptedBookingWithDaysCollision() {
        Booking booking = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID_2, OWNER_ID, PRICE, PERIOD_WITH_COLLISION);
        booking.accept(bookingEventsPublisher);
        return booking;
    }

    private Booking givenOpenBookingWithDaysCollision() {
        return Booking.apartment(RENTAL_PLACE_ID, TENANT_ID_2, OWNER_ID, PRICE, PERIOD_WITH_COLLISION);
    }

    private Booking givenApartmentBooking() {
        return Booking.apartment(RENTAL_PLACE_ID, TENANT_ID_1, OWNER_ID, PRICE, PERIOD);
    }

}