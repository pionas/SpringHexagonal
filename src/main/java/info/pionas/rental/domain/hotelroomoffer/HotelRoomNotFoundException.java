package info.pionas.rental.domain.hotelroomoffer;

public class HotelRoomNotFoundException extends RuntimeException {
    public HotelRoomNotFoundException(String hotelId, int hotelRoomNumber) {
        super("The room with number: " + hotelRoomNumber + " in hotel with id: " + hotelId + " does not exist.");
    }
}
