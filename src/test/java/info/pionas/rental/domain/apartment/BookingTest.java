package info.pionas.rental.domain.apartment;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;

import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Adi
 */
public class BookingTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final List<LocalDate> DAYS = asList(NOW, NOW.plusDays(2));
    private static final String TENANT_ID = "789";
    private static final String RENTAL_PLACE_ID = "456";
    private final EventChannel eventChannel = mock(EventChannel.class);

    @Test
    public void shouldCreateApartmentBookingWithALlRequiredInformation() {
        String rentalPlaceId = "123";
        String tenantId = "456";
        LocalDate start = LocalDate.of(2020, 3, 4);
        LocalDate end = LocalDate.of(2020, 3, 6);
        Booking actual = Booking.apartment(rentalPlaceId, tenantId, new Period(start, end));

        BookingAssertion
                .assertThat(actual)
                .isApartment()
                .isOpen()
                .hasRentalPlaceIdEqualTo(rentalPlaceId)
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(
                        LocalDate.of(2020, 3, 4),
                        LocalDate.of(2020, 3, 5),
                        LocalDate.of(2020, 3, 6)
                );
    }

    @Test
    public void shouldCreateRoomHotelBookingWithALlRequiredInformation() {
        String rentalPlaceId = "123";
        List<LocalDate> days = asList(
                LocalDate.of(2020, 5, 5),
                LocalDate.of(2020, 5, 6),
                LocalDate.of(2020, 5, 7)
        );
        Booking actual = Booking.hotelRoom(rentalPlaceId, TENANT_ID, days);

        BookingAssertion
                .assertThat(actual)
                .isHotelRoom()
                .isOpen()
                .hasRentalPlaceIdEqualTo(rentalPlaceId)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(days);
    }

    @Test
    public void shouldRejectRoomHotelBooking() {
        String id = "123";
        RentalType rentalType = RentalType.APARTMENT;
        List<LocalDate> days = null;
        BookingStatus bookingStatus = BookingStatus.OPEN;

        Booking actual = new Booking(id, rentalType, RENTAL_PLACE_ID, TENANT_ID, days, bookingStatus);
        actual.reject();

        BookingAssertion
                .assertThat(actual)
                .isApartment()
                .isReject()
                .hasRentalPlaceIdEqualTo(RENTAL_PLACE_ID)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(days);
    }

    @Test
    public void shouldChangeStatusOfBookingOnceAccepted() {
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
        booking.accept(eventChannel);

        BookingAssertion.assertThat(booking).isAccept();
    }
    @Test
    public void shouldPublishBookingAcceptedOnceAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
        booking.accept(eventChannel);

        then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        assertThat(actual.getName()).isEqualTo("HOTEL_ROOM");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

}
