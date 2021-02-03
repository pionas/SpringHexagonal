package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferFactory;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartmentOfferApplicationService {
    private final ApartmentOfferRepository apartmentOfferRepository;
    private final ApartmentOfferFactory apartmentOfferFactory;

    public String add(ApartmentOfferDto dto) {
        ApartmentOffer offer = apartmentOfferFactory.create(dto.getApartmentId(), dto.getPrice(), dto.getStart(), dto.getEnd());
        return apartmentOfferRepository.save(offer);
    }

}
