package info.pionas.rental.domain.hotelroomoffer;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class Money {
    private final BigDecimal value;

    static Money of(BigDecimal price) {
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
