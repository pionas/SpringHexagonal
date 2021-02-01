package info.pionas.rental.domain.address;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;

        return new EqualsBuilder()
                .append(street, address.street)
                .append(postalCode, address.postalCode)
                .append(buildingNumber, address.buildingNumber)
                .append(city, address.city)
                .append(country, address.country)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(street)
                .append(postalCode)
                .append(buildingNumber)
                .append(city)
                .append(country)
                .toHashCode();
    }
}
