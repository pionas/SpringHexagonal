package info.pionas.rental.domain.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
class Address {

    private String street;
    private String postalCode;
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String country;

}
