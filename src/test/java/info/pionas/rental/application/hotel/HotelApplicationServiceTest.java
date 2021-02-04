package info.pionas.rental.application.hotel;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.*;
import info.pionas.rental.domain.space.SquareMeterException;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class HotelApplicationServiceTest {
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
    private static final String NO_ID = null;

    private final HotelRepository hotelRepository = mock(HotelRepository.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final HotelApplicationService service = new HotelApplicationServiceFactory().hotelApplicationService(
            hotelRepository, bookingRepository, new FakeEventIdFactory(), new FakeClock(), eventChannel);

    @Test
    void shouldCreateHotel() {
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);

        service.add(givenHotelDto());

        then(hotelRepository).should().save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expected());
    }

    private Hotel expected() {
        return hotel()
                .withName(NAME)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withBuildingNumber(BUILDING_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .build();
    }

    @Test
    void shouldReturnIdOfNewHotel() {
        given(hotelRepository.save(any())).willReturn(HOTEL_ID);

        String actual = service.add(givenHotelDto());

        assertThat(actual).isEqualTo(HOTEL_ID);
    }

    private HotelDto givenHotelDto() {
        return new HotelDto(NAME, STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    @Test
    void shouldCreateHotelRoom() {
        givenExistingHotel();
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);

        service.add(givenHotelRoomDto());

        then(hotelRepository).should().save(captor.capture());
        HotelAssertion.assertThat(captor.getValue())
                .hasOnlyOneHotelRoom(hotelRoom -> {
                    HotelRoomAssertion.assertThat(hotelRoom)
                            .isEqualTo(HotelRoomRequirements.hotelRoom().withRoomNumber(ROOM_NUMBER))
                            .hasSpacesDefinitionEqualTo(SPACES_DEFINITION)
                            .hasDescriptionEqualTo(DESCRIPTION)
                            .hasIdEqualTo(null)
                            .hasNumberEqualTo(ROOM_NUMBER);
                });
    }

    private HotelRoomDto givenHotelRoomDto() {
        return givenHotelRoomDtoWith(SPACES_DEFINITION);
    }

    @Test
    void shouldNotAllowToCreateApartmentWithAtLeastOneSpaceThatHaveSquareMeterEqualZero() {
        givenExistingHotel();
        HotelRoomDto hotelRoomDto = givenHotelRoomDtoWith(ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0, "Room", 0.0));

        SquareMeterException actual = assertThrows(SquareMeterException.class, () -> service.add(hotelRoomDto));

        assertThat(actual).hasMessage("Square meter lower or equal zero");
        then(hotelRepository).should(never()).save(any());
    }

    @Test
    void shouldNotAllowToCreateApartmentWithAtLeastOneSpaceThatHaveSquareMeterLowerThanZero() {
        givenExistingHotel();
        HotelRoomDto hotelRoomDto = givenHotelRoomDtoWith(ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0, "Room", -13.0));

        SquareMeterException actual = assertThrows(SquareMeterException.class, () -> service.add(hotelRoomDto));

        assertThat(actual).hasMessage("Square meter lower or equal zero");
        then(hotelRepository).should(never()).save(any());
    }

    private HotelRoomDto givenHotelRoomDtoWith(Map<String, Double> spacesDefinition) {
        return new HotelRoomDto(HOTEL_ID, ROOM_NUMBER, spacesDefinition, DESCRIPTION);
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

    private HotelRoomBookingDto givenHotelRoomBookingDto() {
        return new HotelRoomBookingDto(HOTEL_ID, ROOM_NUMBER, TENANT_ID, DAYS);
    }

    private void thenBookingShouldBeCreated() {
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        BDDMockito.then(bookingRepository).should().save(captor.capture());

        BookingAssertion.assertThat(captor.getValue()).isEqualToBookingHotelRoom(NO_ID, TENANT_ID, DAYS);
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
}