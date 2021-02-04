package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifierFactory;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    private String ownerId;
    @Embedded
    private Money price;
    @ElementCollection
    private List<LocalDate> days;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.OPEN;

    @Deprecated
    Booking(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        this.rentalType = rentalType;
        this.rentalPlaceId = rentalPlaceId;
        this.tenantId = tenantId;
        this.days = days;
    }

    private Booking(RentalType apartment, String rentalPlaceId, String tenantId, String ownerId, Money price, List<LocalDate> days) {
        this.rentalType = apartment;
        this.rentalPlaceId = rentalPlaceId;
        this.tenantId = tenantId;
        this.ownerId = ownerId;
        this.price = price;
        this.days = days;
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    public static Booking apartment(String rentalPlaceId, String tenantId, String ownerId, Money price, Period period) {
        List<LocalDate> days = period.asDays();
        return new Booking(RentalType.APARTMENT, rentalPlaceId, tenantId, ownerId, price, days);
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

    public UUID id() {
        return getId();
    }

    public boolean hasCollisionWith(Booking booking) {
        return bookingStatus.equals(ACCEPTED) && hasDaysCollisionsWith(booking);
    }

    private boolean hasDaysCollisionsWith(Booking booking) {
        return days.stream().anyMatch(day -> booking.days.contains(day));
    }

    public RentalPlaceIdentifier rentalPlaceIdentifier() {
        return RentalPlaceIdentifierFactory.create(rentalType, rentalPlaceId);
    }

    public boolean isFor(Period period) {
        return getDays().stream().anyMatch(day -> period.contains(day));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Booking booking = (Booking) o;

        if (!days.containsAll(booking.days)) {
            return false;
        }

        return new EqualsBuilder()
                .append(rentalType, booking.rentalType)
                .append(rentalPlaceId, booking.rentalPlaceId)
                .append(tenantId, booking.tenantId)
                .append(ownerId, booking.ownerId)
                .append(price, booking.price)
                .isEquals();
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rentalType)
                .append(rentalPlaceId)
                .append(tenantId)
                .append(ownerId)
                .append(price)
                .append(days)
                .toHashCode();
    }
}
