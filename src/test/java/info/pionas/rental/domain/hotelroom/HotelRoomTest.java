package info.pionas.rental.domain.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingAssertion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

class HotelRoomTest {
    private static final String HOTEL_ID = UUID.randomUUID().toString();
    private static final int ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";
    private static final String TENANT_ID = "325426";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private final HotelRoomFactory factory = new HotelRoomFactory();
    private final HotelRoomEventsPublisher hotelRoomEventsPublisher = mock(HotelRoomEventsPublisher.class);

    @Test
    void shouldCreateHotelRoomWithAllRequiredInformation() {
        HotelRoom actual = factory.create(HOTEL_ID, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        HotelRoomAssertion.assertThat(actual)
                .hasHotelIdEqualTo(HOTEL_ID)
                .hasRoomNumberEqualTo(ROOM_NUMBER)
                .hasSpacesDefinitionEqualTo(SPACES_DEFINITION)
                .hasDescriptionEqualTo(DESCRIPTION);
    }

    @Test
    void shouldCreateBookingOnceBooked() {
        HotelRoom hotelRoom = createHotelRoom();

        Booking actual = hotelRoom.book(TENANT_ID, DAYS, hotelRoomEventsPublisher);

        BookingAssertion.assertThat(actual)
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    @Test
    void shouldPublishHotelRoomBooked() {
        HotelRoom hotelRoom = createHotelRoom();

        hotelRoom.book(TENANT_ID, DAYS, hotelRoomEventsPublisher);

        BDDMockito.then(hotelRoomEventsPublisher).should().publishHotelRoomBooked(ArgumentMatchers.any(), eq(HOTEL_ID), eq(TENANT_ID), eq(DAYS));
    }

    private HotelRoom createHotelRoom() {
        return factory.create(HOTEL_ID, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }
}