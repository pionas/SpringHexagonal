package info.pionas.rental.domain.apartment;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ApartmentRequirements {
    private static final Map<String, Double> SPACES_TO_FULFIL_CONTRACT = ImmutableMap.of("RoomXYZ", 123.45);

    private final Apartment.Builder apartment;

    private ApartmentRequirements(Apartment.Builder apartment) {
        this.apartment = apartment;
    }

    public static ApartmentRequirements apartment() {
        return new ApartmentRequirements(Apartment.Builder.apartment());
    }

    public ApartmentRequirements withOwnerId(String ownerId) {
        apartment.withOwnerId(ownerId);
        return this;
    }

    public ApartmentRequirements withApartmentNumber(String apartmentNumber) {
        apartment.withApartmentNumber(apartmentNumber);
        return this;
    }

    public ApartmentRequirements withAddress(String street, String postalCode, String houseNumber, String city, String country) {
        apartment
                .withStreet(street)
                .withPostalCode(postalCode)
                .withHouseNumber(houseNumber)
                .withCity(city)
                .withCountry(country);
        return this;
    }

    Apartment get() {
        return apartment
                .withSpacesDefinition(SPACES_TO_FULFIL_CONTRACT)
                .build();
    }
}