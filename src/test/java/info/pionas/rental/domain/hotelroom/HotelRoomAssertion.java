package info.pionas.rental.domain.hotelroom;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class HotelRoomAssertion {
    private final HotelRoom actual;

    public static HotelRoomAssertion assertThat(HotelRoom hotelRoo) {
        return new HotelRoomAssertion(hotelRoo);
    }

    public HotelRoomAssertion hasHotelIdEqualTo(String hotelId) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelId", hotelId);
        return this;
    }

    public HotelRoomAssertion hasRoomNumberEqualTo(int number) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("number", number);
        return this;
    }

    public HotelRoomAssertion hasDescriptionEqualTo(String description) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        return this;
    }

    public HotelRoomAssertion hasSpacesDefinitionEqualTo(Map<String, Double> spacesDefinition) {
        Assertions.assertThat(actual).extracting("spaces").satisfies(spacesActual -> {
            List<Space> spaces = (List<Space>) spacesActual;
            Assertions.assertThat(spaces).hasSize(spacesDefinition.size());
            spacesDefinition.forEach((name, squareMeter) -> {
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
}
