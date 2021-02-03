package info.pionas.usermanagment.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {
    private static final String NAME_1 = "Adrian";
    private static final String LAST_NAME_1 = "Pionka";

    @Test
    void shouldCreateUserWithAllRequiredFields() {
        Name actual = createName1();
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("name", NAME_1);
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("lastName", LAST_NAME_1);
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        Name actual = createName1();

        Assertions.assertThat(actual.equals(actual)).isTrue();
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsUser() {
        Name actual = createName1();

        assertThat(actual.equals(null)).isFalse();
    }

    @Test
    void shouldRecognizeTwoUserInstancesRepresentTheSameAggregate() {
        Name toCompare = createName1();
        Name actual = createName1();
        assertThat(actual.equals(toCompare)).isTrue();
        assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    private Name createName1() {
        return new Name(NAME_1, LAST_NAME_1);
    }
}