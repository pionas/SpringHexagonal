package info.pionas.rental.domain.hotelroomoffer;

public class HotelRoomNotFoundException extends RuntimeException {
    public HotelRoomNotFoundException(String hotelRoomId) {
        super("Hotel room with id " + hotelRoomId + " does not exist");
    }
}
