package info.pionas.rental.domain.address;

import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
@EqualsAndHashCode
@SuppressWarnings("PMD.UnusedPrivateField")
public class Address {

    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;
}
