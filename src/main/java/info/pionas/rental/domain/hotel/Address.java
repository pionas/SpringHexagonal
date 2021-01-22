package info.pionas.rental.domain.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Address {

    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;
}
