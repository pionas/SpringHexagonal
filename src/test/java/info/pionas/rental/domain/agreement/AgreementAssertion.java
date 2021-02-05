package info.pionas.rental.domain.agreement;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;

import static info.pionas.rental.domain.agreement.Agreement.Builder.agreement;

@RequiredArgsConstructor
public class AgreementAssertion {
    private final Agreement actual;

    public static AgreementAssertion assertThat(Agreement actual) {
        return new AgreementAssertion(actual);
    }

    public AgreementAssertion isEqualToAgreement(RentalType rentalType, String rentalPlaceId, String ownerId, String tenantId, List<LocalDate> days, Money price) {
        Agreement expected = agreement()
                .withRentalType(rentalType)
                .withRentalPlaceId(rentalPlaceId)
                .withOwnerId(ownerId)
                .withTenantId(tenantId)
                .withDays(days)
                .withPrice(price)
                .build();
        Assertions.assertThat(actual).isEqualTo(expected);
        return this;
    }
}