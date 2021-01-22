package info.pionas.rental.domain.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
class Room {

    private String name;
    @Embedded
    private SquareMeter squareMeter;

}
