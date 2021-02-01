package info.pionas.rental.domain.booking;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class RentalPlaceIdentifier {
    private final RentalType hotelRoom;
    private final String rentalPlaceId;
}
