package info.pionas.rental.domain.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class AddressDto {
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;
}
