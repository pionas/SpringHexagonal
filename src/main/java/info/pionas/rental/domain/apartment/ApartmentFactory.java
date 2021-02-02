package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.owner.OwnerDoesNotExistException;
import info.pionas.rental.domain.owner.OwnerRepository;
import lombok.RequiredArgsConstructor;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;

@RequiredArgsConstructor
public class ApartmentFactory {
    private final OwnerRepository ownerRepository;

    public Apartment create(NewApartmentDto apartmentDto) {
        if (!ownerRepository.exists(apartmentDto.getOwnerId())) {
            throw new OwnerDoesNotExistException(apartmentDto.getOwnerId());
        }
        return apartment()
                .withOwnerId(apartmentDto.getOwnerId())
                .withStreet(apartmentDto.getStreet())
                .withPostalCode(apartmentDto.getPostalCode())
                .withHouseNumber(apartmentDto.getHouseNumber())
                .withApartmentNumber(apartmentDto.getApartmentNumber())
                .withCity(apartmentDto.getCity())
                .withCountry(apartmentDto.getCountry())
                .withDescription(apartmentDto.getDescription())
                .withSpacesDefinition(apartmentDto.getSpacesDefinition())
                .build();
    }
}
