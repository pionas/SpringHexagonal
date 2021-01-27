package info.pionas.rental.application.hotelroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@RequiredArgsConstructor
@Getter
public class HotelRoomDto {

    private final String hotelId;
    private final int number;
    private final Map<String, Double> spacesDefinition;
    private final String description;
}
