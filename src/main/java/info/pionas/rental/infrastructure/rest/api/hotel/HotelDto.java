package info.pionas.rental.infrastructure.rest.api.hotel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class HotelDto {

    private final String name;
    private final String street;
    private final String postalCode;
    private final String buildingNumber;
    private final String city;
    private final String country;
}
