package info.pionas.rental.domain.money;

import java.math.BigDecimal;

public class NotAllowedMoneyValueException extends RuntimeException {
    public NotAllowedMoneyValueException(BigDecimal price) {
        super("Price " + price.toString() + " is not greater than zero");
    }
}
