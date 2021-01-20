package info.pionas.rental.domain.apartment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
public class BookingAccepted {

    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String name;
    private final String rentalPlaceId;
    private final String tenantId;
    private final List<LocalDate> days;

    static BookingAccepted create(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        String eventId = UUID.randomUUID().toString();
        LocalDateTime eventCreationDateTime = LocalDateTime.now();
        return new BookingAccepted(eventId, eventCreationDateTime, rentalType.name(), rentalPlaceId, tenantId, days);
    }

}
