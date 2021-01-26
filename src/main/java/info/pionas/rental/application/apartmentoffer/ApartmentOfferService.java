package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentAvailability;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.domain.apartmentoffer.Money;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
class ApartmentOfferService {
    private final ApartmentOfferRepository repository;

    void add(String apartmentId, BigDecimal price, LocalDate start, LocalDate end) {
        ApartmentOffer offer = new ApartmentOffer(apartmentId, new Money(price), new ApartmentAvailability(start, end));
        repository.save(offer);
    }
}
