package info.pionas.rental.domain.hotelroom;

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
            Assertions.assertThat(spaces).hasSize(expected.size());

            expected.forEach((name, squareMeter) -> {
                Assertions.assertThat(spaces).anySatisfy(hasSpaceThat(name, squareMeter));
            });
        });
        return this;
    }

    private Consumer<Space> hasSpaceThat(String name, Double squareMeter) {
        return space -> Assertions.assertThat(space)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("squareMeter.value", squareMeter);
    }

    public HotelRoomAssertion hasDescriptionEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", expected);
        return this;
    }
}