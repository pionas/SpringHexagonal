package info.pionas.rental.infrastructure.rest.api.hotelroom;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
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
