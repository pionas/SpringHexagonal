package info.pionas.rental.domain.apartment;

import java.time.LocalDate;
import static java.util.Arrays.asList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Adi
 */
public class BookingTest {

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
        String tenantId = "456";
        List<LocalDate> days = asList(
                LocalDate.of(2020, 5, 5),
                LocalDate.of(2020, 5, 6),
                LocalDate.of(2020, 5, 7)
        );
        Booking actual = Booking.hotelRoom(rentalPlaceId, tenantId, days);

        BookingAssertion
                .assertThat(actual)
                .isHotelRoom()
                .isOpen()
                .hasRentalPlaceIdEqualTo(rentalPlaceId)
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(days);
    }

    @Test
    public void shouldRejectRoomHotelBooking() {
        String id = "123";
        RentalType rentalType = RentalType.APARTMENT;
        String rentalPlaceId = "456";
        String tenantId = "789";
        List<LocalDate> days = null;
        BookingStatus bookingStatus = BookingStatus.OPEN;

        Booking actual = new Booking(id, rentalType, rentalPlaceId, tenantId, days, bookingStatus);
        actual.reject();

        BookingAssertion
                .assertThat(actual)
                .isApartment()
                .isReject()
                .hasRentalPlaceIdEqualTo(rentalPlaceId)
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(days);
    }

    @Test
    public void shouldAcceptRoomHotelBooking() {
        String id = "123";
        RentalType rentalType = RentalType.APARTMENT;
        String rentalPlaceId = "456";
        String tenantId = "789";
        List<LocalDate> days = null;
        BookingStatus bookingStatus = BookingStatus.OPEN;

        Booking actual = new Booking(id, rentalType, rentalPlaceId, tenantId, days, bookingStatus);
//        actual.accept(eventChannel);

        BookingAssertion
                .assertThat(actual)
                .isApartment()
                .isOpen()
                .hasRentalPlaceIdEqualTo(rentalPlaceId)
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(days);
    }

}
