package info.pionas.rental.query.hotelroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "HOTEL_ROOM_SPACE")
@Getter
public class SpaceReadModel {

    private final String name;
    private final Double value;
}
