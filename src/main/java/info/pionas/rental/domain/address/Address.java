package info.pionas.rental.domain.address;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateField")
public class Address {

    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;
}
