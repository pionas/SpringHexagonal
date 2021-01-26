package info.pionas.rental.domain.apartmentoffer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class Money {
    private final BigDecimal value;
}
