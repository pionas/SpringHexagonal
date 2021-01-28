package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.space.Space;
import info.pionas.rental.domain.space.SpacesAssertion;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class ApartmentAssertion {
    private final Apartment actual;

    public static ApartmentAssertion assertThat(Apartment actual) {
        return new ApartmentAssertion(actual);
    }

    public ApartmentAssertion hasOwnerIdEqualsTo(String ownerId) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("ownerId", ownerId);
        return this;
    }

    public ApartmentAssertion hasDescriptionEqualsTo(String description) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        return this;
    }

    public ApartmentAssertion hasAddressEqualsTo(
            String street, String postalCode, String houseNumber, String apartmentNumber, String city, String country) {
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("apartmentNumber", apartmentNumber);
        Assertions.assertThat(actual).extracting("address")
                .hasFieldOrPropertyWithValue("street", street)
                .hasFieldOrPropertyWithValue("postalCode", postalCode)
                .hasFieldOrPropertyWithValue("buildingNumber", houseNumber)
                .hasFieldOrPropertyWithValue("city", city)
                .hasFieldOrPropertyWithValue("country", country);

        return this;
    }

    public ApartmentAssertion hasSpacesEqualsTo(Map<String, Double> expected) {
        Assertions.assertThat(actual).extracting("spaces").satisfies(spacesActual -> {
            SpacesAssertion.assertThat((List<Space>) spacesActual)
                    .hasSize(expected.size())
                    .hasAllSpacesFrom(expected);
        });
        return this;
    }

}