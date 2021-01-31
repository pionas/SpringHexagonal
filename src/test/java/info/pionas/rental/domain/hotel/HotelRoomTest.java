package info.pionas.rental.domain.hotel;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static info.pionas.rental.domain.hotel.HotelRoom.Builder.hotelRoom;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

class HotelRoomTest {
    private static final UUID HOTEL_ID_1 = UUID.randomUUID();
    private static final int ROOM_NUMBER_1 = 42;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION_1 = ImmutableMap.of("Room1", 30.0);
    private static final String DESCRIPTION_1 = "This is very nice place";
    private static final UUID HOTEL_ID_2 = UUID.randomUUID();
    private static final int ROOM_NUMBER_2 = 13;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION_2 = ImmutableMap.of("RoomOne", 10.0, "RoomTwo", 25.0);
    private static final String DESCRIPTION_2 = "This is even better place";
    private static final int DIFFERENT_ROOM_NUMBER = ROOM_NUMBER_2;
    private static final String TENANT_ID = "325426";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private final HotelRoomEventsPublisher hotelRoomEventsPublisher = mock(HotelRoomEventsPublisher.class);

    @Test
    void shouldCreateHotelRoomWithAllRequiredInformation() {
        HotelRoom actual = hotelRoom()
                .withHotelId(HOTEL_ID_1)
                .withNumber(ROOM_NUMBER_1)
                .withSpacesDefinition(SPACES_DEFINITION_1)
                .withDescription(DESCRIPTION_1)
                .build();

        HotelRoomAssertion.assertThat(actual)
                .hasHotelIdEqualTo(HOTEL_ID_1)
                .hasRoomNumberEqualTo(ROOM_NUMBER_1)
                .hasSpacesDefinitionEqualTo(SPACES_DEFINITION_1)
                .hasDescriptionEqualTo(DESCRIPTION_1);
    }

    @Test
    void shouldCreateBookingOnceBooked() {
        HotelRoom hotelRoom = givenHotelRoom();

        Booking actual = hotelRoom.book(TENANT_ID, DAYS, hotelRoomEventsPublisher);

        BookingAssertion.assertThat(actual)
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    @Test
    void shouldPublishHotelRoomBooked() {
        HotelRoom hotelRoom = givenHotelRoom();

        hotelRoom.book(TENANT_ID, DAYS, hotelRoomEventsPublisher);

        BDDMockito.then(hotelRoomEventsPublisher).should().publishHotelRoomBooked(any(), eq(HOTEL_ID_1.toString()), eq(TENANT_ID), eq(DAYS));
    }

    @Test
    void shouldRecognizeWhenNumberIsTheSame() {
        boolean actual = givenHotelRoom().hasNumberEqualTo(ROOM_NUMBER_1);

        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void shouldRecognizeWhenNumberIsDifferent() {
        boolean actual = givenHotelRoom().hasNumberEqualTo(DIFFERENT_ROOM_NUMBER);

        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        HotelRoom actual = givenHotelRoom();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfHotelRoomRepresentsTheSameAggregate() {
        HotelRoom toCompare = hotelRoom()
                .withHotelId(HOTEL_ID_1)
                .withNumber(ROOM_NUMBER_1)
                .withSpacesDefinition(SPACES_DEFINITION_2)
                .withDescription(DESCRIPTION_2)
                .build();

        HotelRoom actual = givenHotelRoom();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsHotelRoom() {
        HotelRoom actual = givenHotelRoom();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameHotelRooms")
    void shouldRecognizeHotelRoomsDoesNotRepresentTheSameAggregate(Object toCompare) {
        HotelRoom actual = givenHotelRoom();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private HotelRoom givenHotelRoom() {
        return givenHotelRoomBuilder().build();
    }

    private static Stream<Object> notTeSameHotelRooms() {
        return Stream.of(
                givenHotelRoomBuilder().withHotelId(HOTEL_ID_2).build(),
                givenHotelRoomBuilder().withNumber(ROOM_NUMBER_2).build(),
                new Object()
        );
    }

    private static HotelRoom.Builder givenHotelRoomBuilder() {
        return hotelRoom()
                .withHotelId(HOTEL_ID_1)
                .withNumber(ROOM_NUMBER_1)
                .withSpacesDefinition(SPACES_DEFINITION_1)
                .withDescription(DESCRIPTION_1);
    }
}