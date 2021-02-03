package info.pionas.rental.domain.money;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Money {
    private BigDecimal value;

    public static Money of(BigDecimal price) {
        if (isHigherThanZero(price)) {
            return new Money(price);
        } else {
            throw new NotAllowedMoneyValueException(price);
        }
    }

    private static boolean isHigherThanZero(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }
}
