package info.pionas.rental.query.apartment;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "APARTMENT_ROOM")
class RoomReadModel {

    private final String name;
    private final Double size;
}
