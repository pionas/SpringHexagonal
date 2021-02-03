package info.pionas.rental.domain.rentalplaceidentifier;

import static info.pionas.rental.domain.rentalplaceidentifier.RentalType.APARTMENT;
import static info.pionas.rental.domain.rentalplaceidentifier.RentalType.HOTEL_ROOM;

public class RentalPlaceIdentifierFactory {
    private RentalPlaceIdentifierFactory() {
    }
    public static RentalPlaceIdentifier apartment(String apartmentID) {
        return new RentalPlaceIdentifier(APARTMENT, apartmentID);
    }

    public static RentalPlaceIdentifier hotelRoom(String hotelRoomId) {
        return new RentalPlaceIdentifier(HOTEL_ROOM, hotelRoomId);
    }

    public static RentalPlaceIdentifier create(RentalType rentalType, String rentalPlaceId) {
        return new RentalPlaceIdentifier(rentalType, rentalPlaceId);
    }
}
