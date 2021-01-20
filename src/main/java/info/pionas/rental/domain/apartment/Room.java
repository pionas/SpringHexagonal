package info.pionas.rental.domain.apartment;

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
@Table(name = "APARTMENT_ROOM")
class Room {

    private final String name;
    @Embedded
    private final SquareMeter squareMeter;
}
