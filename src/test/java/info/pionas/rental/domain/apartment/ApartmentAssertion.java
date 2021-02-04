package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.space.Space;
import info.pionas.rental.domain.space.SpacesAssertion;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApartmentAssertion {
    private final Apartment actual;

    public static ApartmentAssertion assertThat(Apartment actual) {
        return new ApartmentAssertion(actual);
    }


    public ApartmentAssertion hasDescriptionEqualsTo(String description) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        return this;
    }

    public ApartmentAssertion hasSpacesEqualsTo(Map<String, Double> expected) {
        Assertions.assertThat(actual).extracting("spaces").satisfies(roomsActual -> {
            SpacesAssertion.assertThat((List<Space>) roomsActual)
                    .hasSize(expected.size())
                    .hasAllSpacesFrom(expected);
        });

        return this;
    }

    public ApartmentAssertion isEqualTo(ApartmentRequirements requirements) {
        Assertions.assertThat(actual).isEqualTo(requirements.get());
        return this;
    }
}