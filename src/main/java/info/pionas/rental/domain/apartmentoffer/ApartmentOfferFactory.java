package info.pionas.rental.domain.apartmentoffer;

import info.pionas.rental.domain.apartment.ApartmentNotFoundException;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;

@RequiredArgsConstructor
public class ApartmentOfferFactory {
    private final ApartmentRepository apartmentRepository;

    public ApartmentOffer create(String apartmentId, BigDecimal price, LocalDate availabilityStart, LocalDate availabilityEnd) {
        if (!apartmentRepository.existById(apartmentId)) {
            throw new ApartmentNotFoundException(apartmentId);
        }
        return apartmentOffer()
                .withApartmentId(apartmentId)
                .withPrice(price)
                .withAvailability(availabilityStart, availabilityEnd)
                .build();
    }
}
