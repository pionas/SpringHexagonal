package info.pionas.rental.domain.booking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class BookingAccepted {

    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String rentalType;
    private final String rentalPlaceId;
    private final String tenantId;
    private final List<LocalDate> days;

    public static class Builder {
        private String eventId;
        private LocalDateTime eventCreationDateTime;
        private String rentalType;
        private String rentalPlaceId;
        private String tenantId;
        private List<LocalDate> days;

        public static Builder bookingAccepted() {
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

        public Builder withRentalType(String rentalType) {
            this.rentalType = rentalType;
            return this;
        }

        public Builder withRentalPlaceId(String rentalPlaceId) {
            this.rentalPlaceId = rentalPlaceId;
            return this;
        }

        public Builder withTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder withDays(List<LocalDate> days) {
            this.days = days;
            return this;
        }

        public BookingAccepted build() {
            return new BookingAccepted(eventId, eventCreationDateTime, rentalType, rentalPlaceId, tenantId, days);
        }
    }
}
