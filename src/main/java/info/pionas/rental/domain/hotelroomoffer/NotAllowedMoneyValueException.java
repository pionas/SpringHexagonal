package info.pionas.rental.domain.hotelroomoffer;

import java.math.BigDecimal;

public class NotAllowedMoneyValueException extends RuntimeException {
    NotAllowedMoneyValueException(BigDecimal price) {
        super("Price " + price.toString() + " is not greater than zero");
    }
}
