package info.pionas.rental.domain.rentalplaceidentifier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RentalPlaceIdentifierFactoryTest {
    @Test
    void shouldCreateRentalPlaceForHotelRoom() {
        RentalPlaceIdentifier actual = RentalPlaceIdentifierFactory.hotelRoom("123");

        assertThat(actual.getRentalPlaceId()).isEqualTo("123");
        assertThat(actual.getRentalType()).isEqualTo(RentalType.HOTEL_ROOM);
    }

    @Test
    void shouldCreateRentalPlaceForApartment() {
        RentalPlaceIdentifier actual = RentalPlaceIdentifierFactory.apartment("123");

        assertThat(actual.getRentalPlaceId()).isEqualTo("123");
        assertThat(actual.getRentalType()).isEqualTo(RentalType.APARTMENT);
    }
}