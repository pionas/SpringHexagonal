package info.pionas.rental.domain.apartment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
class Address {

    private String street;
    private String postalCode;
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String country;

}
