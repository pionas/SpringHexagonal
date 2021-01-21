package info.pionas.rental.domain.apartment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
class SquareMeter {

    private Double size;

}
