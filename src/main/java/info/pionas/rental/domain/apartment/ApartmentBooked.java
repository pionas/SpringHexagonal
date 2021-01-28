package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.period.Period;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class ApartmentBooked {

    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String apartmentId;
    private final String ownerId;
    private final String tenantId;
    private final LocalDate periodStart;
    private final LocalDate periodEnd;

    public static class Builder {
        private String eventId;
        private LocalDateTime eventCreationDateTime;
        private String apartmentId;
        private String ownerId;
        private String tenantId;
        private LocalDate periodStart;
        private LocalDate periodEnd;

        public static Builder apartmentBooked() {
            return new Builder();
        }

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withEventCreationDateTime(LocalDateTime eventCreationDateTime) {
            this.eventCreationDateTime = eventCreationDateTime;
            return this;
        }

        public Builder withApartmentId(String apartmentId) {
            this.apartmentId = apartmentId;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder withTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder withPeriod(Period period) {
            this.periodStart = period.getPeriodStart();
            this.periodEnd = period.getPeriodEnd();
            return this;
        }

        public ApartmentBooked build() {
            return new ApartmentBooked(eventId, eventCreationDateTime, apartmentId, ownerId, tenantId, periodStart, periodEnd);
        }
    }
}
