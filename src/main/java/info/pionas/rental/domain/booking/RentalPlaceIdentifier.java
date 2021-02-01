package info.pionas.rental.domain.booking;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public
class RentalPlaceIdentifier {
    private final RentalType hotelRoom;
    private final String rentalPlaceId;

}
