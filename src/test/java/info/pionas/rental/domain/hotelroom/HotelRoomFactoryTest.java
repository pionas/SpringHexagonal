package info.pionas.rental.domain.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.space.Space;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;


public class HotelRoomFactoryTest {

    @Test
    public void shouldCreateHotelRoomWillAllRequiredFields() {
        String hotelId = "123";
        int number = 20;
        Map<String, Double> spacesDefinition = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
        String description = "Room with jacuzzi";

        HotelRoom actual = new HotelRoomFactory().create(
                hotelId,
                number,
                spacesDefinition,
                description
        );

        assertThat(actual).hasFieldOrPropertyWithValue("hotelId", hotelId);
        assertThat(actual).hasFieldOrPropertyWithValue("number", number);
        assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        assertThatHasSpaces(actual, spacesDefinition);
    }

    private void assertThatHasSpaces(HotelRoom actual, Map<String, Double> spacesDefinition) {
        assertThat(actual).extracting("spaces").satisfies(spacesActual -> {
            List<Space> spaces = (List<Space>) spacesActual;
            assertThat(spaces).hasSize(spacesDefinition.size());
            spacesDefinition.forEach((name, squareMeter) -> {
                assertThat(spaces).anySatisfy(hasSpaceThat(name, squareMeter));
            });
        });
    }

    private Consumer<Space> hasSpaceThat(String name, Double squareMeter) {
        return space -> assertThat(space)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("squareMeter.value", squareMeter);
    }
}
