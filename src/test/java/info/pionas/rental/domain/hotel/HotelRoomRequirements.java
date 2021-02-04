package info.pionas.rental.domain.hotel;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.UUID;

public class HotelRoomRequirements {
    private static final Map<String, Double> SPACES_TO_FULFIL_CONTRACT = ImmutableMap.of("RoomXYZ", 123.45);
    private final HotelRoom.Builder hotelRoom;

    private HotelRoomRequirements(HotelRoom.Builder hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    public static HotelRoomRequirements hotelRoom() {
        return new HotelRoomRequirements(HotelRoom.Builder.hotelRoom());
    }

    public HotelRoomRequirements withHotelId(UUID hotelId) {
        hotelRoom.withHotelId(hotelId);
        return this;
    }

    public HotelRoomRequirements withRoomNumber(int roomNumber) {
        hotelRoom.withNumber(roomNumber);
        return this;
    }

    HotelRoom get() {
        return hotelRoom.withSpacesDefinition(SPACES_TO_FULFIL_CONTRACT).build();
    }
}
