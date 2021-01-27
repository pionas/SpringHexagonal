package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.address.Address;
import info.pionas.rental.domain.booking.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "APARTMENT")
@SuppressWarnings("PMD.UnusedPrivateField")
public class Apartment {

    @Id
    @GeneratedValue
    private UUID id;

    private String ownerId;

    private String apartmentNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "buildingNumber", column = @Column(name = "house_number"))
    })
    private Address address;

    @ElementCollection
    @CollectionTable(name = "APARTMENT_ROOM", joinColumns = @JoinColumn(name = "APARTMENT_ID"))
    private List<Room> rooms;

    private String description;

    private Apartment(String ownerId, Address address, String apartmentNumber, List<Room> rooms, String description) {
        this.ownerId = ownerId;
        this.address = address;
        this.apartmentNumber = apartmentNumber;
        this.rooms = rooms;
        this.description = description;
    }

    public Booking book(String tenantId, Period period, ApartmentEventsPublisher publisher) {
        publisher.publishApartmentBooked(id(), ownerId, tenantId, period);
        return Booking.apartment(id(), tenantId, period);
    }

    public String id() {
        if (id == null) {
            return null;
        }
        return id.toString();
    }

    public static class Builder {
        private String ownerId;
        private String street;
        private String postalCode;
        private String houseNumber;
        private String apartmentNumber;
        private String city;
        private String country;
        private String description;
        private Map<String, Double> roomsDefinition;

        private Builder() {
        }

        public static Builder apartment() {
            return new Builder();
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
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

        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder withApartmentNumber(String apartmentNumber) {
            this.apartmentNumber = apartmentNumber;
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

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withRoomsDefinition(Map<String, Double> roomsDefinition) {
            this.roomsDefinition = roomsDefinition;
            return this;
        }

        public Apartment build() {
            return new Apartment(ownerId, address(), apartmentNumber, rooms(), description);
        }

        private Address address() {
            return new Address(street, postalCode, houseNumber, city, country);
        }

        private List<Room> rooms() {
            List<Room> rooms = new ArrayList<>();
            roomsDefinition.forEach((name, size) -> {
                SquareMeter squareMeter = new SquareMeter(size);
                rooms.add(new Room(name, squareMeter));
            });

            return rooms;
        }
    }
}
