package info.pionas.rental.domain.booking;

public class RentalPlaceIdentifierTestFactory {
    public static RentalPlaceIdentifier hotelRoom(String rentalPlaceId) {
        return RentalPlaceIdentifier.hotelRoom(rentalPlaceId);
    }

    public static RentalPlaceIdentifier apartment(String apartmentId) {
        return RentalPlaceIdentifier.apartment(apartmentId);
    }
}
