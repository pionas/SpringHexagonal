package info.pionas.rental.domain.apartment;


public class ApartmentBookedTestFactory {
    public static ApartmentBooked create(String eventId, String apartmentId, String ownerId, String tenantId, Period period) {
        return ApartmentBooked.create(eventId, apartmentId, ownerId, tenantId, period);
    }
}