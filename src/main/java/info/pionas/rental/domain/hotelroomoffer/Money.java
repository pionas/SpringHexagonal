package info.pionas.rental.domain.hotelroomoffer;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class Money {
    private final BigDecimal value;

    static Money of(BigDecimal price) {
        return new Money(price);
    }
}
