package info.pionas.rental.domain.hotelroomoffer;

public class HotelRoomNotFoundException extends RuntimeException {

    public static RuntimeException ofHotelNumber(int number) {
        return new HotelRoomNotFoundException("Hotel room with number " + number + " does not exist");
    }

    public static RuntimeException ofHotelRoomId(String hotelRoomId) {
        return new HotelRoomNotFoundException("Hotel room with id " + hotelRoomId + " does not exist");
    }

    private HotelRoomNotFoundException(String message) {
        super(message);
    }

}
