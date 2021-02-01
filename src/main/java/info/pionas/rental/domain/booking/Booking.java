package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.period.Period;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.booking.BookingStatus.ACCEPTED;
import static info.pionas.rental.domain.booking.BookingStatus.REJECTED;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "BOOKING")
@SuppressWarnings("PMD.UnusedPrivateField")
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

    public void reject(BookingEventsPublisher bookingEventsPublisher) {
        bookingStatus = bookingStatus.moveTo(REJECTED);
        bookingEventsPublisher.bookingRejected(rentalType, rentalPlaceId, tenantId, days);
    }

    public void accept(BookingEventsPublisher bookingEventsPublisher) {
        bookingStatus = bookingStatus.moveTo(ACCEPTED);
        bookingEventsPublisher.bookingAccepted(rentalType, rentalPlaceId, tenantId, days);
    }

    public String id() {
        return id.toString();
    }
}
