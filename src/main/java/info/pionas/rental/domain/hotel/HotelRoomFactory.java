package info.pionas.rental.domain.hotel;

import info.pionas.rental.domain.space.Space;
import info.pionas.rental.domain.space.SquareMeter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class HotelRoomFactory {

    public HotelRoom create(UUID hotelId, int number, Map<String, Double> spacesDefinition, String description) {
        List<Space> spaces = spacesDefinition.entrySet().stream()
                .map(entry -> {
                    SquareMeter squareMeter = new SquareMeter(entry.getValue());
                    return new Space(entry.getKey(), squareMeter);
                })
                .collect(Collectors.toList());

        return new HotelRoom(hotelId, number, spaces, description);
    }
}
