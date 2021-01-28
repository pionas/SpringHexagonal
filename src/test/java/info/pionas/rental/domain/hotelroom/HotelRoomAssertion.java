package info.pionas.rental.domain.hotelroom;

import info.pionas.rental.domain.space.Space;
import info.pionas.rental.domain.space.SpacesAssertion;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelRoomAssertion {
    private final HotelRoom actual;

    public static HotelRoomAssertion assertThat(HotelRoom actual) {
        return new HotelRoomAssertion(actual);
    }

    public HotelRoomAssertion hasHotelIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelId", expected);
        return this;
    }

    public HotelRoomAssertion hasRoomNumberEqualTo(int expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("number", expected);
        return this;
    }

    public HotelRoomAssertion hasSpacesDefinitionEqualTo(Map<String, Double> expected) {
        Assertions.assertThat(actual).extracting("spaces").satisfies(spacesActual -> {
            List<Space> spaces = (List<Space>) spacesActual;
            SpacesAssertion.assertThat(spaces)
                    .hasSize(expected.size())
                    .hasAllSpacesFrom(expected);
        });
        return this;
    }

    public HotelRoomAssertion hasDescriptionEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", expected);
        return this;
    }
}