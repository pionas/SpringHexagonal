package info.pionas.rental.domain.apartment;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
class SquareMeter {

    private Double size;

}
