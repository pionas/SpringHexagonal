package info.pionas.rental.domain.apartment;

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

        public static ApartmentBooked.Builder apartmentBooked() {
            return new ApartmentBooked.Builder();
        }

        public ApartmentBooked.Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public ApartmentBooked.Builder withEventCreationDateTime(LocalDateTime eventCreationDateTime) {
            this.eventCreationDateTime = eventCreationDateTime;
            return this;
        }

        public ApartmentBooked.Builder withApartmentId(String apartmentId) {
            this.apartmentId = apartmentId;
            return this;
        }

        public ApartmentBooked.Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public ApartmentBooked.Builder withTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public ApartmentBooked.Builder withPeriodStart(LocalDate periodStart) {
            this.periodStart = periodStart;
            return this;
        }

        public ApartmentBooked.Builder withPeriodEnd(LocalDate periodEnd) {
            this.periodEnd = periodEnd;
            return this;
        }

        public Builder withPeriod(Period period) {
            this.periodStart = period.getStart();
            this.periodEnd = period.getEnd();
            return this;
        }

        public ApartmentBooked build() {
            return new ApartmentBooked(eventId, eventCreationDateTime, apartmentId, ownerId, tenantId, periodStart, periodEnd);
        }
    }
}
