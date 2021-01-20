package info.pionas.rental.domain.apartment;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

/**
 * @author Adi
 */
@RequiredArgsConstructor
class ApartmentAsseration {

    private final Apartment actual;

    static ApartmentAsseration assertThat(Apartment apartment) {
        return new ApartmentAsseration(apartment);
    }

    ApartmentAsseration hasOwnerIdEqualsTo(String ownerId) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("ownerId", ownerId);
        return this;
    }

    ApartmentAsseration hasDescriptionEqualsTo(String description) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        return this;
    }

    ApartmentAsseration hasAddressEqualsTo(String street, String postalCode, String houseNumber, String apartmentNumber, String city, String country) {
        Assertions.assertThat(actual).extracting("address")
                .hasFieldOrPropertyWithValue("street", street)
                .hasFieldOrPropertyWithValue("postalCode", postalCode)
                .hasFieldOrPropertyWithValue("houseNumber", houseNumber)
                .hasFieldOrPropertyWithValue("apartmentNumber", apartmentNumber)
                .hasFieldOrPropertyWithValue("city", city)
                .hasFieldOrPropertyWithValue("country", country);
        return this;
    }

    ApartmentAsseration hasRoomsEqualsTo(Map<String, Double> roomsDefinition) {
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
