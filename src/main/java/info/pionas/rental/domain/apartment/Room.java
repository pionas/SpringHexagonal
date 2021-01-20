package info.pionas.rental.domain.apartment;

import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@Embeddable
class Room {

    private final String name;
    @Embedded
    private final SquareMeter squareMeter;
}
