package info.pionas.rental.query.hotelroom;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
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
