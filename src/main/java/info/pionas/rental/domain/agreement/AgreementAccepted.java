package info.pionas.rental.domain.agreement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class AgreementAccepted {
    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String rentalType;
    private final String rentalPlaceId;
    private final String ownerId;
    private final String tenantId;
    private final List<LocalDate> days;
    private final BigDecimal price;
}
