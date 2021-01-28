package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.period.Period;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;

import static info.pionas.rental.domain.booking.BookingAssertion.assertThat;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;

class BookingTest {
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final String TENANT_ID = "1234";
    private static final String RENTAL_PLACE_ID = "5748";

    private final EventChannel eventChannel = mock(EventChannel.class);

    @Test
    void shouldCreateBookingForApartment() {
        Period period = new Period(LocalDate.of(2020, 3, 4), LocalDate.of(2020, 3, 6));

        Booking actual = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, period);

        assertThat(actual)
                .isOpen()
                .isApartment()
                .hasRentalPlaceIdEqualTo(RENTAL_PLACE_ID)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(LocalDate.of(2020, 3, 4), LocalDate.of(2020, 3, 5), LocalDate.of(2020, 3, 6));
    }

    @Test
    void shouldCreateBookingForHotelRoom() {
        List<LocalDate> days = asList(LocalDate.of(2020, 6, 1), LocalDate.of(2020, 6, 2), LocalDate.of(2020, 6, 4));

        Booking actual = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, days);

        assertThat(actual)
                .isOpen()
                .isHotelRoom()
                .hasRentalPlaceIdEqualTo(RENTAL_PLACE_ID)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(days);
    }

    @Test
    void shouldChangeStatusOfBookingOnceAccepted() {
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);

        booking.accept(eventChannel);

        assertThat(booking).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedOnceAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);

        booking.accept(eventChannel);

        BDDMockito.then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        Assertions.assertThat(actual.getRentalType()).isEqualTo("HOTEL_ROOM");
        Assertions.assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        Assertions.assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        Assertions.assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    @Test
    void shouldChangeStatusOfBookingOnceRejected() {
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);

        booking.reject();

        assertThat(booking).isRejected();
    }
}