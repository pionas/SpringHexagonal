package info.pionas.rental.domain.apartment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
class Room {

    private String name;
    @Embedded
    private SquareMeter squareMeter;

}
