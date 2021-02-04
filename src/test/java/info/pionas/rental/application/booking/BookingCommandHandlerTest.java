package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.*;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifierFactory;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingCommandHandlerTest {
    private static final String BOOKING_ID = "74398";
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID = "5678";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));

    private final ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final FakeEventIdFactory eventIdFactory = new FakeEventIdFactory();
    private final FakeClock clock = new FakeClock();
    private final BookingCommandHandler commandHandler = new BookingCommandHandlerFactory().bookingCommandHandler(
            bookingRepository, eventIdFactory, clock, eventChannel);

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
        assertThat(actual.getRentalType()).isEqualTo("HOTEL_ROOM");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    private void givenBookingsWithoutCollision() {
        givenBookings(asList(
                Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS),
                Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS)));
    }

    @Test
    void shouldRejectBookingWhenBookingsWithCollisionFound() {
        givenBookingsWithCollision();
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isRejected();
    }

    private void givenBookingsWithCollision() {
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
        booking.accept(new BookingEventsPublisher(eventIdFactory, clock, eventChannel));
        givenBookings(asList(booking, Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS)));
    }

    private void givenBookings(List<Booking> bookings) {
        RentalPlaceIdentifier identifier = RentalPlaceIdentifierFactory.hotelRoom(RENTAL_PLACE_ID);
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
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
        given(bookingRepository.findById(BOOKING_ID)).willReturn(booking);
    }
}