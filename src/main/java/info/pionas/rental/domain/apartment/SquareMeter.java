package info.pionas.rental.domain.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateMethod")
class SquareMeter {

    private Double size;

}
