package info.pionas.rental.domain.hotel;

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
public class Address {

    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;
}
