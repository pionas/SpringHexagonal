package info.pionas.rental.domain.booking;

import static info.pionas.rental.domain.booking.RentalType.HOTEL_ROOM;

public class RentalPlaceIdentifierTestFactory {
    public static RentalPlaceIdentifier hotelRoom(String rentalPlaceId) {
        return new RentalPlaceIdentifier(HOTEL_ROOM, rentalPlaceId);
    }
}
