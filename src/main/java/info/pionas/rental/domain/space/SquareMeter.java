package info.pionas.rental.domain.space;

import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class SquareMeter {

    private Double value;

    static SquareMeter of(Double value) {
        if (value <= 0) {
            throw new SquareMeterException();
        }
        return new SquareMeter(value);
    }
}
