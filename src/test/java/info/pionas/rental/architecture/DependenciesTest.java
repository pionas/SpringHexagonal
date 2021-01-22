package info.pionas.rental.architecture;

import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

public class DependenciesTest {
    @Test
    void shouldHaveNoCycles() {
        SlicesRuleDefinition.slices()
                .matching("info.pionas.rental(*)..")
                .should().beFreeOfCycles()
        ;
    }
}
