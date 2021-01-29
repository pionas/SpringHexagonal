package info.pionas.rental.domain.hotel;

import info.pionas.rental.domain.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static info.pionas.rental.domain.hotel.HotelRoom.Builder.hotelRoom;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "HOTEL")
@SuppressWarnings("PMD.UnusedPrivateField")
public class Hotel {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;
    private String name;
    @Embedded
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "HOTEL_ID", referencedColumnName = "ID")
    private List<HotelRoom> hotelRooms = new ArrayList<>();

    private Hotel(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String id() {
        return id.toString();
    }

    public void addRoom(int number, Map<String, Double> spacesDefinition, String description) {
        HotelRoom hotelRoom = hotelRoom()
                .withHotelId(id())
                .withNumber(number)
                .withSpacesDefinition(spacesDefinition)
                .withDescription(description)
                .build();
        hotelRooms.add(hotelRoom);
    }

    public String getIdOfRoom(int number) {
        return getHotelRooms(number).id();
    }

    private HotelRoom getHotelRooms(int number) {
        return hotelRooms.stream()
                .filter(hotelRoom -> hotelRoom.hasNumberEqualTo(number))
                .findFirst()
                .get();
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
