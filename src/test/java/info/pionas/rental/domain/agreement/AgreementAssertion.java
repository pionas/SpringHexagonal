package info.pionas.rental.domain.agreement;

import info.pionas.rental.domain.money.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public
class AgreementAssertion {
    private final Agreement actual;

    public static AgreementAssertion assertThat(Agreement actual) {
        return new AgreementAssertion(actual);
    }

    public AgreementAssertion isEqualToAgreement(String rentalPlaceId, String tenantId, String ownerId, List<LocalDate> days, Money price) {
//        Assertions.assertThat(actual).isEqualTo(Booking.apartment(rentalPlaceId, tenantId, ownerId, price, days));
        return this;
    }
}