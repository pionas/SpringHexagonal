package info.pionas.rental.domain.hotel;

import javax.persistence.Embeddable;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@Embeddable
public class Address {

    private final String street;
    private final String postalCode;
    private final String buildingNumber;
    private final String city;
    private final String country;
}
