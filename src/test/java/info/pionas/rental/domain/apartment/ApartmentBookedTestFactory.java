package info.pionas.rental.domain.apartment;

import java.time.LocalDateTime;

public class ApartmentBookedTestFactory {
    public static ApartmentBooked create(String eventId, String apartmentId, String ownerId, String tenantId, Period period) {
        return ApartmentBooked.create(eventId, apartmentId, ownerId, tenantId, period);
    }
    public static ApartmentBooked create(String eventId, LocalDateTime eventCreationDateTime, String apartmentId, String ownerId, String tenantId, Period period) {
        return ApartmentBooked.create(eventId, eventCreationDateTime, apartmentId, ownerId, tenantId, period);
    }
}
