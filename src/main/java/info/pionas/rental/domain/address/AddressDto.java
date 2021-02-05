package info.pionas.rental.domain.address;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AddressDto {
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDto that = (AddressDto) o;

        return new EqualsBuilder()
                .append(getStreet(), that.street)
                .append(getPostalCode(), that.postalCode)
                .append(getBuildingNumber(), that.buildingNumber)
                .append(getCity(), that.city)
                .append(getCountry(), that.country)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getStreet())
                .append(getPostalCode())
                .append(getBuildingNumber())
                .append(getCity())
                .append(getCountry())
                .toHashCode();
    }
}
