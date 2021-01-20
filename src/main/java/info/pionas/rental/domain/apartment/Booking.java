package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.eventchannel.EventChannel;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@AllArgsConstructor
//@Entity
public class Booking {

    @Id
    @GeneratedValue
    private String id;
    private final RentalType rentalType;
    private final String rentalPlaceId;
    private final String tenantId;
    private final List<LocalDate> days;
    private BookingStatus bookingStatus = BookingStatus.OPEN;

    public static Booking apartment(String rentalPlaceId, String tenantId, Period period) {
        List<LocalDate> days = period.asDays();
        return new Booking(RentalType.APARTMENT, rentalPlaceId, tenantId, days);
    }

    public static Booking hotelRoom(String rentalPlaceId, String tenantId, List<LocalDate> days) {
        return new Booking(RentalType.HOTEL_ROOM, rentalPlaceId, tenantId, days);
    }

    public void reject() {
        bookingStatus = BookingStatus.REJECTED;
    }

    public void accept(EventChannel eventChannel) {
        bookingStatus = BookingStatus.ACCEPTED;
        BookingAccepted bookingAccepted = BookingAccepted.create(rentalType, rentalPlaceId, tenantId, days);

        eventChannel.publish(bookingAccepted);
    }
}
