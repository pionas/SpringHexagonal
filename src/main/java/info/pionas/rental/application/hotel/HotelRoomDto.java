package info.pionas.rental.application.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class HotelRoomDto {
    private final String hotelId;
    private final int number;
    private final Map<String, Double> spacesDefinition;
    private final String description;
}
