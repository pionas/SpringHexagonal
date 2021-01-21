package info.pionas.rental.domain.apartment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @author Adi
 */
public class ApartmentBookedTest {

    @Test
    public void shouldCreateEventWithAllInformation() {
        String apartmentId = "1234";
        String ownerId = "5678";
        String tenantId = "3456";
        LocalDate periodStart = LocalDate.of(2020, 1, 1);
        LocalDate periodEnd = LocalDate.of(2020, 2, 1);
        Period period = new Period(periodStart, periodEnd);
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);

        ApartmentBooked actual = ApartmentBooked.create(apartmentId, ownerId, tenantId, period);

        Assertions.assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        Assertions.assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        Assertions.assertThat(actual.getApartmentId()).isEqualTo(apartmentId);
        Assertions.assertThat(actual.getOwnerId()).isEqualTo(ownerId);
        Assertions.assertThat(actual.getTenantId()).isEqualTo(tenantId);
        Assertions.assertThat(actual.getPeriodStart()).isEqualTo(periodStart);
        Assertions.assertThat(actual.getPeriodEnd()).isEqualTo(periodEnd);
    }

}
