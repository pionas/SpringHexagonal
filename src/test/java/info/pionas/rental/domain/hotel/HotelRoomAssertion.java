package info.pionas.rental.domain.hotel;

import info.pionas.rental.domain.space.Space;
import info.pionas.rental.domain.space.SpacesAssertion;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelRoomAssertion {
    private final HotelRoom actual;

    public static HotelRoomAssertion assertThat(HotelRoom actual) {
        return new HotelRoomAssertion(actual);
    }

    public HotelRoomAssertion hasSpacesDefinitionEqualTo(Map<String, Double> expected) {
        Assertions.assertThat(actual).extracting("spaces").satisfies(spacesActual -> {
            SpacesAssertion.assertThat((List<Space>) spacesActual)
                    .hasSize(expected.size())
                    .hasAllSpacesFrom(expected);
        });

        return this;
    }

    public HotelRoomAssertion hasDescriptionEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", expected);
        return this;
    }

    public HotelRoomAssertion isEqualTo(HotelRoomRequirements requirements) {
        Assertions.assertThat(actual).isEqualTo(requirements.get());
        return this;
    }
}