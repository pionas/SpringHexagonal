package info.pionas.rental.domain.hotelroom;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * @author Adi
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Embeddable
class SquareMeter {

    private Double value;
}
