package info.pionas.rental.domain.hotelroom;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "HOTEL_ROOM_SPACE")
public class Space {

    private final String name;
    @Embedded
    private final SquareMeter squareMeter;
}
