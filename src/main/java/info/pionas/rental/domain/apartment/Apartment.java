package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.address.Address;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifierFactory;
import info.pionas.rental.domain.space.Space;
import info.pionas.rental.domain.space.SpacesFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
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
    @AttributeOverrides({
            @AttributeOverride(name = "squareMeter.value", column = @Column(name = "size"))
    })
    private List<Space> spaces;

    private String description;

    private Apartment(String ownerId, Address address, String apartmentNumber, List<Space> spaces, String description) {
        this.ownerId = ownerId;
        this.address = address;
        this.apartmentNumber = apartmentNumber;
        this.spaces = spaces;
        this.description = description;
    }

    @Deprecated
    public Booking book(List<Booking> bookings, String tenantId, Period period, ApartmentEventsPublisher publisher) {
        if (areInGivenPeriod(bookings, period)) {
            throw new ApartmentBookingException();
        }
        publisher.publishApartmentBooked(id(), getOwnerId(), tenantId, period);
        return Booking.apartment(id(), tenantId, getOwnerId(), Money.of(BigDecimal.valueOf(42)), period);
    }

    public Booking book(ApartmentBooking apartmentBooking) {
        Period period = apartmentBooking.getPeriod();
        String tenantId = apartmentBooking.getTenantId();
        if (areInGivenPeriod(apartmentBooking.getBookings(), period)) {
            throw new ApartmentBookingException();
        }
        apartmentBooking.getApartmentEventsPublisher().publishApartmentBooked(id(), getOwnerId(), tenantId, period);
        return Booking.apartment(id(), tenantId, getOwnerId(), apartmentBooking.getPrice(), period);

    }


    private boolean areInGivenPeriod(List<Booking> bookings, Period period) {
        return bookings != null && bookings.stream().anyMatch(booking -> booking.isFor(period));
    }

    public String id() {
        if (id == null) {
            return null;
        }
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Apartment apartment = (Apartment) o;

        return new EqualsBuilder()
                .append(ownerId, apartment.ownerId)
                .append(apartmentNumber, apartment.apartmentNumber)
                .append(address, apartment.address)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(ownerId)
                .append(apartmentNumber)
                .append(address)
                .toHashCode();
    }

    public RentalPlaceIdentifier rentalPlaceIdentifier() {
        return RentalPlaceIdentifierFactory.apartment(id());
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
        private Map<String, Double> spacesDefinition = new HashMap<>();

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

        public Builder withSpacesDefinition(Map<String, Double> spacesDefinition) {
            this.spacesDefinition = spacesDefinition;
            return this;
        }

        public Apartment build() {
            return new Apartment(ownerId, address(), apartmentNumber, spaces(), description);
        }

        private Address address() {
            return new Address(street, postalCode, houseNumber, city, country);
        }

        private List<Space> spaces() {
            return SpacesFactory.create(spacesDefinition);
        }
    }
}
