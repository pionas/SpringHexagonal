package info.pionas.rental.domain.agreement;

import info.pionas.rental.domain.money.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static info.pionas.rental.domain.agreement.Agreement.Builder.agreement;
import static info.pionas.rental.domain.rentalplaceidentifier.RentalType.APARTMENT;
import static java.util.Arrays.asList;

class AgreementTest {
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID = "5678";
    private static final String OWNER_ID = "1982";
    private static final Money PRICE = Money.of(BigDecimal.valueOf(42));
    public static final LocalDate TODAY = LocalDate.now();
    private static final List<LocalDate> DAYS = asList(TODAY, TODAY.plusDays(1));

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        Agreement actual = givenAgreement();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsAgreement() {
        Agreement actual = givenAgreement();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameAgreements")
    void shouldRecognizeAgreementsDoesNotRepresentTheSameAggregate(Object toCompare) {
        Agreement actual = givenAgreement();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private static Stream<Object> notTeSameAgreements() {
        return Stream.of(
                getAgreementBuilder().withOwnerId("212121").build(),
                new Object()
        );
    }

    private Agreement givenAgreement() {
        return getAgreementBuilder()
                .build();
    }

    private static Agreement.Builder getAgreementBuilder() {
        return agreement()
                .withRentalType(APARTMENT)
                .withRentalPlaceId(RENTAL_PLACE_ID)
                .withOwnerId(OWNER_ID)
                .withTenantId(TENANT_ID)
                .withDays(DAYS)
                .withPrice(PRICE);
    }
}