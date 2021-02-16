package info.pionas.rental.domain.agreement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AgreementAcceptedTestFactory {

    public static AgreementAccepted create(String eventId, LocalDateTime eventCreationDateTime, String rentalType, String rentalPlaceId, String ownerId, String tenantId, List<LocalDate> days, BigDecimal price) {
        return new AgreementAccepted(eventId, eventCreationDateTime, rentalType, rentalPlaceId, ownerId, tenantId, days, price);
    }
}