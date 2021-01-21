package info.pionas.rental.domain.hotelroom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author Adi
 */
@AllArgsConstructor
@Embeddable
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
class SquareMeter {

    private final Double value;
}
