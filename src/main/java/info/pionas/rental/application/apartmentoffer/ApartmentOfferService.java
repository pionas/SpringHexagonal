package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartment.ApartmentNotFoundException;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import lombok.RequiredArgsConstructor;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;

@RequiredArgsConstructor
class ApartmentOfferService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentOfferRepository apartmentOfferRepository;

    void add(ApartmentOfferDto dto) {
        if (!apartmentRepository.existById(dto.getApartmentId())) {
            throw new ApartmentNotFoundException(dto.getApartmentId());
        }
        ApartmentOffer offer = apartmentOffer()
                .withApartmentId(dto.getApartmentId())
                .withPrice(dto.getPrice())
                .withAvailability(dto.getStart(), dto.getEnd())
                .build();
        apartmentOfferRepository.save(offer);
    }
}
