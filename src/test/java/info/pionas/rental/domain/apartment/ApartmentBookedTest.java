package info.pionas.rental.domain.apartment;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


class ApartmentBookedTest {
    @Test
    void shouldCreateEventWithAllInformation() {
        String apartmentId = "1234";
        String ownerId = "5678";
        String tenantId = "3456";
        LocalDate periodStart = LocalDate.of(2020, 1, 1);
        LocalDate periodEnd = LocalDate.of(2020, 2, 1);
        Period period = new Period(periodStart, periodEnd);
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);

        ApartmentBooked actual = ApartmentBooked.create(apartmentId, ownerId, tenantId, period);

        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getApartmentId()).isEqualTo(apartmentId);
        assertThat(actual.getOwnerId()).isEqualTo(ownerId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getPeriodStart()).isEqualTo(periodStart);
        assertThat(actual.getPeriodEnd()).isEqualTo(periodEnd);
    }
}