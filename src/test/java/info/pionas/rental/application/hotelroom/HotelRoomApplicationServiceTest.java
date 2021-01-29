package info.pionas.rental.application.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.*;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class HotelRoomApplicationServiceTest {
    private static final UUID HOTEL_ID = UUID.randomUUID();
    private static final int ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";
    private static final String TENANT_ID = "4321";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final String HOTEL_ROOM_ID = "7821321";

    private final HotelRepository hotelRepository = Mockito.mock(HotelRepository.class);
    private final HotelRoomRepository hotelRoomRepository = Mockito.mock(HotelRoomRepository.class);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final EventChannel eventChannel = Mockito.mock(EventChannel.class);
    private final HotelRoomApplicationService service = new HotelRoomApplicationServiceFactory().hotelRoomApplicationService(hotelRepository, hotelRoomRepository, bookingRepository, eventChannel);
    private final HotelRoomFactory factory = new HotelRoomFactory();

    @Test
    void shouldCreateHotelRoom() {
        ArgumentCaptor<HotelRoom> captor = ArgumentCaptor.forClass(HotelRoom.class);
        given(hotelRepository.save(any())).willReturn(HOTEL_ID.toString());

        service.add(createHotelRoomDto());

        then(hotelRoomRepository).should().save(captor.capture());
        HotelRoomAssertion.assertThat(captor.getValue())
                .hasHotelIdEqualTo(HOTEL_ID)
                .hasRoomNumberEqualTo(ROOM_NUMBER)
                .hasSpacesDefinitionEqualTo(SPACES_DEFINITION)
                .hasDescriptionEqualTo(DESCRIPTION);
    }

    @Test
    void shouldReturnIdOfNewHotelRoom() {
        given(hotelRepository.save(any())).willReturn(HOTEL_ID.toString());
        given(hotelRoomRepository.save(any())).willReturn(HOTEL_ROOM_ID);

        String actual = service.add(createHotelRoomDto());

        Assertions.assertThat(actual).isEqualTo(HOTEL_ROOM_ID);
    }

    @Test
    void shouldCreateBookingWhenHotelRoomBooked() {
        String hotelRoomId = "1234";
        givenHotelRoom(hotelRoomId);

        service.book(givenHotelRoomBookingDto(hotelRoomId));

        thenBookingShouldBeCreated();
    }

    @Test
    void shouldPublishHotelRoomBookedEvent() {
        ArgumentCaptor<HotelRoomBooked> captor = ArgumentCaptor.forClass(HotelRoomBooked.class);
        String hotelRoomId = "1234";
        givenHotelRoom(hotelRoomId);

        service.book(givenHotelRoomBookingDto(hotelRoomId));

        then(eventChannel).should().publish(captor.capture());
        HotelRoomBooked actual = captor.getValue();
        assertThat(actual.getEventId()).isEqualTo(FakeEventIdFactory.UUID);
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getHotelId()).isEqualTo(HOTEL_ID.toString());
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
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
        given(hotelRoomRepository.findById(hotelRoomId)).willReturn(hotelRoom);
    }

    private HotelRoom createHotelRoom() {
        return factory.create(HOTEL_ID, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }

    private HotelRoomDto createHotelRoomDto() {
        return new HotelRoomDto(HOTEL_ID.toString(), ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }

    private HotelRoomBookingDto givenHotelRoomBookingDto(String hotelRoomId) {
        return new HotelRoomBookingDto(hotelRoomId, TENANT_ID, DAYS);
    }

}