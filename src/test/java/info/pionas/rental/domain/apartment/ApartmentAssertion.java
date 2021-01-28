package info.pionas.rental.domain.apartment;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@RequiredArgsConstructor
public class ApartmentAssertion {
    private final Apartment actual;

    public static ApartmentAssertion assertThat(Apartment actual) {
        return new ApartmentAssertion(actual);
    }

    public ApartmentAssertion hasOwnerIdEqualsTo(String ownerId) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("ownerId", ownerId);
        return this;
    }

    public ApartmentAssertion hasDescriptionEqualsTo(String description) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        return this;
    }

    public ApartmentAssertion hasAddressEqualsTo(
            String street, String postalCode, String houseNumber, String apartmentNumber, String city, String country) {
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("apartmentNumber", apartmentNumber);
        Assertions.assertThat(actual).extracting("address")
                .hasFieldOrPropertyWithValue("street", street)
                .hasFieldOrPropertyWithValue("postalCode", postalCode)
                .hasFieldOrPropertyWithValue("buildingNumber", houseNumber)
                .hasFieldOrPropertyWithValue("city", city)
                .hasFieldOrPropertyWithValue("country", country);

        return this;
    }

    public ApartmentAssertion hasRoomsEqualsTo(Map<String, Double> roomsDefinition) {
        Assertions.assertThat(actual).extracting("rooms").satisfies(roomsActual -> {
            List<Room> rooms = (List<Room>) roomsActual;
            Assertions.assertThat(rooms).hasSize(roomsDefinition.size());

            roomsDefinition.forEach((name, squareMeter) -> {
                Assertions.assertThat(rooms).anySatisfy(hasRoomThat(name, squareMeter));
            });
        });

        return this;
    }

    private Consumer<Room> hasRoomThat(String name, Double squareMeter) {
        return room -> Assertions.assertThat(room)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("squareMeter.size", squareMeter);
    }
}