package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingDomainServiceTest {
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID_1 = "5678";
    private static final String TENANT_ID_2 = "1234";
    public static final LocalDate TODAY = LocalDate.now();
    private static final List<LocalDate> DAYS = asList(TODAY, TODAY.plusDays(1));
    private static final List<LocalDate> DAYS_WITH_COLLISION = asList(TODAY, TODAY.minusDays(13), TODAY.plusDays(13));
    private static final List<LocalDate> DAYS_WITHOUT_COLLISION = asList(TODAY.minusDays(13), TODAY.plusDays(13));
    private static final List<Booking> NO_BOOKINGS_FOUND = emptyList();
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingDomainService service = new BookingDomainServiceFactory().create(new FakeEventIdFactory(), new FakeClock(), eventChannel);

    @Test
    void shouldAcceptBookingWhenNoOtherBookingsFound() {
        Booking booking = givenBooking();
        service.accept(booking, NO_BOOKINGS_FOUND);

        BookingAssertion.assertThat(booking).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedEventWhenBookingIsAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);
        Booking booking = givenBooking();
        service.accept(booking, NO_BOOKINGS_FOUND);

        then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        assertThat(actual.getRentalType()).isEqualTo("HOTEL_ROOM");
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID_1);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    @Test
    void shouldRejectBookingWHenOtherWithDaysCollisionFound() {
        Booking booking = givenBooking();
        List<Booking> bookings = asList(givenBookingWithDaysCollision());
        service.accept(booking, bookings);

        BookingAssertion.assertThat(booking).isRejected();
    }

    @Test
    void shouldPublishBookingRejectedEventWhenBookingIsRejected() {
        ArgumentCaptor<BookingRejected> captor = ArgumentCaptor.forClass(BookingRejected.class);
        Booking booking = givenBooking();
        List<Booking> bookings = asList(givenBookingWithDaysCollision());
        service.accept(booking, bookings);

        then(eventChannel).should().publish(captor.capture());
        BookingRejected actual = captor.getValue();
        assertThat(actual.getRentalType()).isEqualTo("HOTEL_ROOM");
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID_1);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }


    @Test
    void shouldAcceptBookingWhenOtherWithoutDaysCollision() {
        Booking booking = givenBooking();
        List<Booking> bookings = asList(givenBookingWithoutDaysCollision());
        service.accept(booking, bookings);

        BookingAssertion.assertThat(booking).isAccepted();
    }

    private Booking givenBookingWithoutDaysCollision() {
        return Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID_2, DAYS_WITHOUT_COLLISION);
    }

    private Booking givenBookingWithDaysCollision() {
        return Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID_2, DAYS_WITH_COLLISION);
    }

    private Booking givenBooking() {
        return Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID_1, DAYS);
    }
}