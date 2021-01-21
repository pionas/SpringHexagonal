package info.pionas.rental.infrastructure.rest.api.hotelroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
public class HotelRoomDto {

    private final String hotelId;
    private final int number;
    private final Map<String, Double> spacesDefinition;
    private final String description;
}
