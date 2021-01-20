package info.pionas.rental.domain.apartment;

import javax.persistence.Embeddable;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@Embeddable
class SquareMeter {

    private final Double size;

}
