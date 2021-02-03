package info.pionas.rental.domain.rentalplaceidentifier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RentalPlaceIdentifierTest {

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        RentalPlaceIdentifier actual = givenRentalPlaceIdentifier();

        Assertions.assertThat(actual.equals(actual)).isTrue();
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsRentalPlaceIdentifier() {
        RentalPlaceIdentifier actual = givenRentalPlaceIdentifier();

        assertThat(actual.equals(null)).isFalse();
    }

    @Test
    void shouldRecognizeTwoRentalPlaceIdentifierInstancesRepresentTheSameAggregate() {
        RentalPlaceIdentifier toCompare = givenRentalPlaceIdentifier();
        RentalPlaceIdentifier actual = givenRentalPlaceIdentifier();
        assertThat(actual.equals(toCompare)).isTrue();
        assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    private RentalPlaceIdentifier givenRentalPlaceIdentifier() {
        return new RentalPlaceIdentifier(RentalType.HOTEL_ROOM, "121");
    }
}