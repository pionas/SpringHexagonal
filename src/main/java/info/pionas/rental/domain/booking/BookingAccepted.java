package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.event.EventIdFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class BookingAccepted {

    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String rentalType;
    private final String rentalPlaceId;
    private final String tenantId;
    private final List<LocalDate> days;

    static BookingAccepted create(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        String eventId = new EventIdFactory().create();
        LocalDateTime eventCreationDateTime = LocalDateTime.now();
        return new BookingAccepted(eventId, eventCreationDateTime, rentalType.name(), rentalPlaceId, tenantId, days);
    }

}
