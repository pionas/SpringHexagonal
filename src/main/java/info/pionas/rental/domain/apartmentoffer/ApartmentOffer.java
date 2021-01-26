package info.pionas.rental.domain.apartmentoffer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public
class ApartmentOffer {
    private final String apartmentId;
    private final Money money;
    private final ApartmentAvailability availability;
}
