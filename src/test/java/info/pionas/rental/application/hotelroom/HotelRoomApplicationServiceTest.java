package info.pionas.rental.application.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.*;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomNotFoundException;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class HotelRoomApplicationServiceTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";
    private static final String HOTEL_ID = "1234567";
    private static final int ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";
    private static final String TENANT_ID = "4321";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final String HOTEL_ROOM_ID = "7821321";

    private final HotelRepository hotelRepository = mock(HotelRepository.class);
    private final HotelRoomRepository hotelRoomRepository = mock(HotelRoomRepository.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final HotelRoomApplicationService service = new HotelRoomApplicationServiceFactory().hotelRoomApplicationService(hotelRepository, bookingRepository, eventChannel);

    @Test
    void shouldCreateHotelRoom() {
        givenExistingHotel();
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);

        service.add(givenHotelRoomDto());

        then(hotelRepository).should().save(captor.capture());
        HotelAssertion.assertThat(captor.getValue())
                .hasOnlyOneHotelRoom(hotelRoom -> {
                    HotelRoomAssertion.assertThat(hotelRoom)
                            .hasRoomNumberEqualTo(ROOM_NUMBER)
                            .hasSpacesDefinitionEqualTo(SPACES_DEFINITION)
                            .hasDescriptionEqualTo(DESCRIPTION);
                });
    }

    @Test
    void shouldReturnIdOfNewHotelRoom() {
        givenExistingHotel();

        String actual = service.add(givenHotelRoomDto());

        assertThat(actual).isNull();
    }

    @Test
    void shouldCreateBookingWhenHotelRoomBooked() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        service.book(givenHotelRoomBookingDto());

        thenBookingShouldBeCreated();
    }

    @Test
    void shouldPublishHotelRoomBookedEvent() {
        ArgumentCaptor<HotelRoomBooked> captor = ArgumentCaptor.forClass(HotelRoomBooked.class);
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        service.book(givenHotelRoomBookingDto());

        then(eventChannel).should().publish(captor.capture());
        HotelRoomBooked actual = captor.getValue();
        assertThat(actual.getEventId()).isEqualTo(FakeEventIdFactory.UUID);
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    @Test
    void shouldThrowExceptionWhenHotelRoomNumberNotFound() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
        Executable executable = () -> service.book(new HotelRoomBookingDto(HOTEL_ID, ROOM_NUMBER * 100, TENANT_ID, DAYS));

        HotelRoomNotFoundException actual = assertThrows(HotelRoomNotFoundException.class, executable);

        assertThat(actual).hasMessage("Hotel room with number 1300 does not exist");

    }

    private void thenBookingShouldBeCreated() {
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        BDDMockito.then(bookingRepository).should().save(captor.capture());

        BookingAssertion.assertThat(captor.getValue())
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    private Hotel givenExistingHotel() {
        Hotel hotel = hotel()
                .withName(NAME)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withBuildingNumber(BUILDING_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .build();
        given(hotelRepository.findById(HOTEL_ID)).willReturn(hotel);

        return hotel;
    }

    private HotelRoomDto givenHotelRoomDto() {
        return new HotelRoomDto(HOTEL_ID, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }

    private HotelRoomBookingDto givenHotelRoomBookingDto() {
        return new HotelRoomBookingDto(HOTEL_ID, ROOM_NUMBER, TENANT_ID, DAYS);
    }

}