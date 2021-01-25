package info.pionas.rental.domain.hotel;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "HOTEL")
@SuppressWarnings("PMD.UnusedPrivateField")
public class Hotel {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Embedded
    private Address address;

    private Hotel(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String id() {
        return id.toString();
    }

    public static class Builder {
        private String name;
        private String street;
        private String postalCode;
        private String buildingNumber;
        private String city;
        private String country;

        public static Builder hotel() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder withBuildingNumber(String buildingNumber) {
            this.buildingNumber = buildingNumber;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Hotel build() {
            return new Hotel(name, address());
        }

        private Address address() {
            return new Address(street, postalCode, buildingNumber, city, country);
        }
    }
}
