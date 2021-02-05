package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.address.AddressDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class NewApartmentDto {
    private final String ownerId;
    private final String street;
    private final String postalCode;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;
    private final String description;
    private final Map<String, Double> spacesDefinition;

    public AddressDto addressDto() {
        return new AddressDto(getStreet(), getPostalCode(), getHouseNumber(), getCity(), getCountry());
    }
}
