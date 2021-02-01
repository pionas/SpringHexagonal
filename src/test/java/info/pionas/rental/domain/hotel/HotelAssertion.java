package info.pionas.rental.domain.hotel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelAssertion {

    private final Hotel actual;

    public static HotelAssertion assertThat(Hotel hotel) {
        return new HotelAssertion(hotel);
    }

    public HotelAssertion hasNameEqualsTo(String name) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("name", name);
        return this;
    }

    public HotelAssertion hasAddressEqualsTo(String street, String postalCode, String buildingNumber, String city, String country) {
        Assertions.assertThat(actual).extracting("address")
                .hasFieldOrPropertyWithValue("street", street)
                .hasFieldOrPropertyWithValue("postalCode", postalCode)
                .hasFieldOrPropertyWithValue("buildingNumber", buildingNumber)
                .hasFieldOrPropertyWithValue("city", city)
                .hasFieldOrPropertyWithValue("country", country);
        return this;
    }

    public HotelAssertion hasOnlyOneHotelRoom(Consumer<HotelRoom> requirements) {
        hasHotelRooms(1);
        return hasHotelRoom(requirements);
    }

    public HotelAssertion hasHotelRoom(Consumer<HotelRoom> requirements) {
        Assertions.assertThat(actual).extracting("hotelRooms").satisfies(rooms -> {
            Assertions.assertThat((List<HotelRoom>) rooms).anySatisfy(requirements);
        });

        return this;
    }

    public HotelAssertion hasHotelRooms(int expected) {
        Assertions.assertThat(actual).extracting("hotelRooms").satisfies(rooms -> {
            Assertions.assertThat((List<HotelRoom>) rooms).hasSize(expected);
        });

        return this;
    }

}