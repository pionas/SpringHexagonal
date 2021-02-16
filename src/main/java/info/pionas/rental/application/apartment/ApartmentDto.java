package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.NewApartmentDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static info.pionas.rental.domain.apartment.NewApartmentDto.Builder.newApartmentDto;


@RequiredArgsConstructor
@Getter
public class ApartmentDto {

    private final String ownerId;
    private final String street;
    private final String postalCode;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;
    private final String description;
    private final Map<String, Double> spacesDefinition;

    public NewApartmentDto asNewApartmentDto() {
        return newApartmentDto()
                .withOwnerId(getOwnerId())
                .withStreet(getStreet())
                .withPostalCode(getPostalCode())
                .withHouseNumber(getHouseNumber())
                .withApartmentNumber(getApartmentNumber())
                .withCity(getCity())
                .withCountry(getCountry())
                .withDescription(getDescription())
                .withSpacesDefinition(getSpacesDefinition())
                .build();
    }
}
