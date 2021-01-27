package info.pionas.rental.infrastructure.persistence.jpa.hotelroomoffer;

class HotelRoomOfferDoesNotExistException extends RuntimeException {

    HotelRoomOfferDoesNotExistException(String hotelRoomOfferID) {
        super("Hotel room offer with id " + hotelRoomOfferID + " does not exist");
    }
}

