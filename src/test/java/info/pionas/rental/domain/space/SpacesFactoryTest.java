package info.pionas.rental.domain.space;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpacesFactoryTest {
    @Test
    void shouldCreateSpaceWithAllRequiredFields() {
        Map<String, Double> spacesDefinition = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
        List<Space> actual = SpacesFactory.create(spacesDefinition);

        SpacesAssertion.assertThat(actual)
                .hasSize(spacesDefinition.size())
                .hasAllSpacesFrom(spacesDefinition);
    }

    @Test
    void shouldThrowExceptionWhenSpacesIsEmpty() {
        Map<String, Double> spacesDefinition = new HashMap<>();
        NotEnoughSpacesGivenException actual = assertThrows(NotEnoughSpacesGivenException.class, () -> {
            SpacesFactory.create(spacesDefinition);
        });
        assertThat(actual).hasMessage("No spaces given");
    }
}