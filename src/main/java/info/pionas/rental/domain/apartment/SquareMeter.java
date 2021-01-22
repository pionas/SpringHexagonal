package info.pionas.rental.domain.apartment;

import lombok.*;

import javax.persistence.Embeddable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
class SquareMeter {

    private Double size;

}
