package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;

@RequiredArgsConstructor
class ApartmentOfferService {
    private final ApartmentOfferRepository repository;

    void add(String apartmentId, BigDecimal price, LocalDate start, LocalDate end) {
        ApartmentOffer offer = apartmentOffer()
                .withApartmentId(apartmentId)
                .withPrice(price)
                .withAvailability(start, end)
                .build();
        repository.save(offer);
    }
}
