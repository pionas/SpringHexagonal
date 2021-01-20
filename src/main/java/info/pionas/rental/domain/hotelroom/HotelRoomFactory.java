package info.pionas.rental.domain.hotelroom;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Adi
 */
public class HotelRoomFactory {

    public HotelRoom create(String hotelId, int number, Map<String, Double> spacesDefinition, String description) {
        List<Space> spaces = spacesDefinition.entrySet().stream()
                .map(entry -> {
                    SquareMeter squareMeter = new SquareMeter(entry.getValue());
                    return new Space(entry.getKey(), squareMeter);
                })
                .collect(Collectors.toList());

        return new HotelRoom(hotelId, number, spaces, description);
    }
}
