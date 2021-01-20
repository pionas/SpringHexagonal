package info.pionas.rental.application.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingAssertion;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotelroom.HotelRoom;
import info.pionas.rental.domain.hotelroom.HotelRoomFactory;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.then;

class HotelRoomApplicationServiceTest {
    private static final String TENANT_ID = "4321";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private final HotelRoomRepository hotelRoomRepository = Mockito.mock(HotelRoomRepository.class);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final EventChannel eventChannel = Mockito.mock(EventChannel.class);

    private final HotelRoomApplicationService service = new HotelRoomApplicationService(
            hotelRoomRepository,
            bookingRepository,
            eventChannel
    );

    @Test
    void shouldCreateBookingWHenHotelRoomBooked() {
        String hotelRoomId = "1234";
        givenHotelRoom(hotelRoomId);

        service.book(hotelRoomId, TENANT_ID, DAYS);

        thenBookingShouldBeCreated();
    }

    @Test
    void shouldCreateHotelRoomWithAllInformation() {
        ArgumentCaptor<HotelRoom> captor = ArgumentCaptor.forClass(HotelRoom.class);
        String hotelId = "123";
        int number = 20;
        Map<String, Double> spacesDefinition = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
        String description = "Room with jacuzzi";

        service.add(hotelId, number, spacesDefinition, description);

        then(hotelRoomRepository).should().save(captor.capture());

    }

    private void thenBookingShouldBeCreated() {
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        BDDMockito.then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    private void givenHotelRoom(String hotelRoomId) {
        HotelRoom hotelRoom = createHotelRoom();
        BDDMockito.given(hotelRoomRepository.findById(hotelRoomId)).willReturn(hotelRoom);
    }

    private HotelRoom createHotelRoom() {
        return new HotelRoomFactory().create(
                "",
                42,
                ImmutableMap.of("Room1", 30.0),
                "This is very nice place"
        );
    }
}