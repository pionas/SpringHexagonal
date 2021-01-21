package info.pionas.rental.domain.hotelroom;

import lombok.RequiredArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Table;

/**
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
