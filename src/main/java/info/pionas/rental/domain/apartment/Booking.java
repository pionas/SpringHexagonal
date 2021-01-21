package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "BOOKING")
public class Booking {

    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private RentalType rentalType;
    private String rentalPlaceId;
    private String tenantId;
    @ElementCollection
    private List<LocalDate> days;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.OPEN;

    Booking(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        this.rentalType = rentalType;
        this.rentalPlaceId = rentalPlaceId;
        this.tenantId = tenantId;
        this.days = days;
    }

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

    public String id() {
        return id.toString();
    }
}
