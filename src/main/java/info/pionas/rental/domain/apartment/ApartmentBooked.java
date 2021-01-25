package info.pionas.rental.domain.apartment;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public final class ApartmentBooked {

    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String apartmentId;
    private final String ownerId;
    private final String tenantId;
    private final LocalDate periodStart;
    private final LocalDate periodEnd;

    private ApartmentBooked(String eventId, LocalDateTime eventCreationDateTime, String apartmentId, String ownerId, String tenantId, Period period) {
        this.eventId = eventId;
        this.eventCreationDateTime = eventCreationDateTime;
        this.apartmentId = apartmentId;
        this.ownerId = ownerId;
        this.tenantId = tenantId;
        this.periodStart = period.getStart();
        this.periodEnd = period.getEnd();
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    static ApartmentBooked create(String eventId, String apartmentId, String ownerId, String tenantId, Period period) {
        LocalDateTime eventCreationDateTime = LocalDateTime.now();
        return new ApartmentBooked(eventId, eventCreationDateTime, apartmentId, ownerId, tenantId, period);
    }
}
