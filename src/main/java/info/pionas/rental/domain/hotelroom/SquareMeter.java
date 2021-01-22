package info.pionas.rental.domain.hotelroom;

import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateMethod")
class SquareMeter {

    private Double value;
}
