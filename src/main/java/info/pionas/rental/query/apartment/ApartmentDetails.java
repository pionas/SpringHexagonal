package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApartmentDetails {

    private final ApartmentReadModel apartment;
    private final ApartmentBookingHistoryReadModel bookingHistory;
}
