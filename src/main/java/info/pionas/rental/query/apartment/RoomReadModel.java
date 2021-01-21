package info.pionas.rental.query.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * @author Adi
 */
@AllArgsConstructor
@Getter
@Embeddable
class RoomReadModel {

    private String name;
    private Double size;
}
