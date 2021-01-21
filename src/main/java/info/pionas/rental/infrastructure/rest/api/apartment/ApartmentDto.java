package info.pionas.rental.infrastructure.rest.api.apartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
class ApartmentDto {

    private final String ownerId;
    private final String street;
    private final String postalCode;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;
    private final String description;
    private final Map<String, Double> roomsDefinition;
}
