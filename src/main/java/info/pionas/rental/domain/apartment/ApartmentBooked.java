package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.event.EventIdFactory;
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

    static ApartmentBooked create(String apartmentId, String ownerId, String tenantId, Period period) {
        String eventId = new EventIdFactory().create();
        return create(eventId, apartmentId, ownerId, tenantId, period);
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    static ApartmentBooked create(String eventId, String apartmentId, String ownerId, String tenantId, Period period) {
        LocalDateTime eventCreationDateTime = LocalDateTime.now();
        return new ApartmentBooked(eventId, eventCreationDateTime, apartmentId, ownerId, tenantId, period);
    }
}
