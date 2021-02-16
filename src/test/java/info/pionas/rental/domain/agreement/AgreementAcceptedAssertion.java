package info.pionas.rental.domain.agreement;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class AgreementAcceptedAssertion {
    private final AgreementAccepted actual;

    public static AgreementAcceptedAssertion assertThat(AgreementAccepted actual) {
        return new AgreementAcceptedAssertion(actual);
    }

    public AgreementAcceptedAssertion hasEventId(String expected) {
        Assertions.assertThat(actual.getEventId()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasEventCreationDateTime(LocalDateTime expected) {
        Assertions.assertThat(actual.getEventCreationDateTime()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasRentalType(String expected) {
        Assertions.assertThat(actual.getRentalType()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasRentalPlaceId(String expected) {
        Assertions.assertThat(actual.getRentalPlaceId()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasOwnerId(String expected) {
        Assertions.assertThat(actual.getOwnerId()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasTenantId(String expected) {
        Assertions.assertThat(actual.getTenantId()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasPrice(BigDecimal expected) {
        Assertions.assertThat(actual.getPrice()).isEqualTo(expected);
        return this;
    }

    public AgreementAcceptedAssertion hasDays(List<LocalDate> expected) {
        Assertions.assertThat(actual.getDays()).isEqualTo(expected);
        return this;
    }
}