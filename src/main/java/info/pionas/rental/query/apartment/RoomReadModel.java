package info.pionas.rental.query.apartment;

import lombok.RequiredArgsConstructor;

import javax.persistence.Table;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "APARTMENT_ROOM")
class RoomReadModel {

    private final String name;
    private final Double size;
}
