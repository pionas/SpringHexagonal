package info.pionas.rental.domain.booking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static info.pionas.rental.domain.booking.RentalType.APARTMENT;
import static info.pionas.rental.domain.booking.RentalType.HOTEL_ROOM;

@RequiredArgsConstructor
@Getter
public class RentalPlaceIdentifier {
    private final RentalType rentalType;
    private final String rentalPlaceId;

    public static RentalPlaceIdentifier apartment(String apartmentID) {
        return new RentalPlaceIdentifier(APARTMENT, apartmentID);
    }

    public static RentalPlaceIdentifier hotelRoom(String hotelRoomId) {
        return new RentalPlaceIdentifier(HOTEL_ROOM, hotelRoomId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RentalPlaceIdentifier that = (RentalPlaceIdentifier) o;

        return new EqualsBuilder()
                .append(rentalType, that.rentalType)
                .append(rentalPlaceId, that.rentalPlaceId)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rentalType)
                .append(rentalPlaceId)
                .toHashCode();
    }
}
