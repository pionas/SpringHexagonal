package info.pionas.rental.domain.apartmentoffer;

import java.math.BigDecimal;

public class NotAllowedMoneyValueException extends RuntimeException {
    NotAllowedMoneyValueException(BigDecimal price) {
        super("Price " + price.toString() + " is lower than zero");
    }
}
