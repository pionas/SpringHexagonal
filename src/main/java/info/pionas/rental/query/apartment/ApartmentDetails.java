package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Adi
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApartmentDetails {
    private static final ApartmentReadModel NO_APARTMENT = null;
    private static final ApartmentBookingHistoryReadModel NO_HISTORY = null;
    private final ApartmentReadModel apartment;
    private final ApartmentBookingHistoryReadModel bookingHistory;

    static ApartmentDetails notExisting() {
        return new ApartmentDetails(NO_APARTMENT, NO_HISTORY);
    }

    static ApartmentDetails withoutHistory(ApartmentReadModel apartmentReadModel) {
        return new ApartmentDetails(apartmentReadModel, NO_HISTORY);
    }

    static ApartmentDetails withHistory(ApartmentReadModel apartmentReadModel, ApartmentBookingHistoryReadModel apartmentBookingHistoryReadModel) {
        return new ApartmentDetails(apartmentReadModel, apartmentBookingHistoryReadModel);
    }
}
