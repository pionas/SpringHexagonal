package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartment.ApartmentNotFoundException;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;

@RequiredArgsConstructor
class ApartmentOfferService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentOfferRepository apartmentOfferRepository;

    void add(String apartmentId, BigDecimal price, LocalDate start, LocalDate end) {
        if (!apartmentRepository.existById(apartmentId)) {
            throw new ApartmentNotFoundException("Apartment with id " + apartmentId + " does not exist");
        }
        ApartmentOffer offer = apartmentOffer()
                .withApartmentId(apartmentId)
                .withPrice(price)
                .withAvailability(start, end)
                .build();
        apartmentOfferRepository.save(offer);
    }
}
