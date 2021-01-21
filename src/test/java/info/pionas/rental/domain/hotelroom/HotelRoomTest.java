package info.pionas.rental.domain.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingAssertion;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;

class HotelRoomTest {
    private static final UUID HOTEL_ROOM_ID = UUID.randomUUID();
    private static final String HOTEL_ID = "123";
    private static final int NUMBER = 20;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final String DESCRIPTION = "Room with jacuzzi";
    private static final LocalDate NOW = LocalDate.now();
    private static final List<LocalDate> DAYS = asList(NOW, NOW.plusDays(2));
    private static final String TENANT_ID = "789";
    private final EventChannel eventChannel = mock(EventChannel.class);

    @Test
    void shouldCreateBookingOnceBooked() {
        HotelRoom hotelRoom = createHotelRoom();
        Booking actual = hotelRoom.book(TENANT_ID, DAYS, eventChannel);

        BookingAssertion.assertThat(actual)
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    private HotelRoom createHotelRoom() {
        HotelRoom hotelRoom = new HotelRoomFactory().create(
                HOTEL_ID,
                NUMBER,
                SPACES_DEFINITION,
                DESCRIPTION
        );
        hotelRoom.setId(HOTEL_ROOM_ID);
        return hotelRoom;
    }
}