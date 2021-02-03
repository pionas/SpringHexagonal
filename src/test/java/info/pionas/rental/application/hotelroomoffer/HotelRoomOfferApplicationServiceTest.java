package info.pionas.rental.application.hotelroomoffer;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomNotFoundException;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import info.pionas.rental.domain.money.NotAllowedMoneyValueException;
import info.pionas.rental.domain.period.PeriodException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class HotelRoomOfferApplicationServiceTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";
    private static final String HOTEL_ROOM_ID = "1234";
    private static final String HOTEL_ID = "123";
    private static final int ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 12, 10);
    private static final LocalDate END = LocalDate.of(2041, 12, 20);
    private static final LocalDate NO_DATE = null;
    private static final LocalDate START_YEAR_LATER = LocalDate.of(2041, 12, 10);
    private final HotelRepository hotelRepository = mock(HotelRepository.class);
    private final HotelRoomOfferRepository repository = mock(HotelRoomOfferRepository.class);
    private final HotelRoomOfferApplicationService service = new HotelRoomOfferApplicationServiceFactory().create(repository, hotelRepository);

    @Test
    void shouldCreateHotelRoomOffer() {
        ArgumentCaptor<HotelRoomOffer> captor = ArgumentCaptor.forClass(HotelRoomOffer.class);
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
        service.add(new HotelRoomOfferDto(HOTEL_ID, ROOM_NUMBER, HOTEL_ROOM_ID, PRICE, START, END));

        then(repository).should().save(captor.capture());
        HotelRoomOfferAssertion.assertThat(captor.getValue())
                .hasHotelRoomIdEqualTo(HOTEL_ROOM_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    void shouldRecognizeHotelRoomDoesNotExist() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
        HotelRoomNotFoundException actual = assertThrows(HotelRoomNotFoundException.class, () -> {
            service.add(new HotelRoomOfferDto(HOTEL_ID, (ROOM_NUMBER * 100), HOTEL_ROOM_ID, PRICE, START, END));
        });
        assertThat(actual).hasMessage("Hotel room with id " + HOTEL_ROOM_ID + " does not exist");
    }

    @Test
    void shouldRecognizePriceIsNotHigherThanZero() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        HotelRoomOfferDto dto = new HotelRoomOfferDto(HOTEL_ID, ROOM_NUMBER, HOTEL_ROOM_ID, BigDecimal.ZERO, START, END);
        NotAllowedMoneyValueException actual = assertThrows(NotAllowedMoneyValueException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Price 0 is not greater than zero");
    }

    @Test
    void shouldRecognizeAvailabilityStartIsAfterEnd() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        HotelRoomOfferDto dto = new HotelRoomOfferDto(HOTEL_ID, ROOM_NUMBER, HOTEL_ROOM_ID, PRICE, END, START);
        PeriodException actual = assertThrows(PeriodException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Start date: 2041-12-20 of period is after end date: 2040-12-10");
    }

    @Test
    void shouldRecognizeAvailabilityStartDateIsFromPast() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        HotelRoomOfferDto dto = new HotelRoomOfferDto(HOTEL_ID, ROOM_NUMBER, HOTEL_ROOM_ID, PRICE, LocalDate.of(2020, 10, 10), END);
        PeriodException actual = assertThrows(PeriodException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Start date: 2020-10-10 is past date");
    }

    @Test
    void shouldRecognizeAvailabilityStartDateIsFromPastWhenEndNotGiven() {
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        HotelRoomOfferDto dto = new HotelRoomOfferDto(HOTEL_ID, ROOM_NUMBER, HOTEL_ROOM_ID, PRICE, LocalDate.of(2020, 10, 10), NO_DATE);
        PeriodException actual = assertThrows(PeriodException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Start date: 2020-10-10 is past date");
    }

    @Test
    void shouldCreateHotelRoomOfferWhenAvailabilityEndNotGiven() {
        ArgumentCaptor<HotelRoomOffer> captor = ArgumentCaptor.forClass(HotelRoomOffer.class);
        Hotel hotel = givenExistingHotel();
        hotel.addRoom(ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);

        HotelRoomOfferDto dto = new HotelRoomOfferDto(HOTEL_ID, ROOM_NUMBER, HOTEL_ROOM_ID, PRICE, START, NO_DATE);
        service.add(dto);

        then(repository).should().save(captor.capture());
        HotelRoomOfferAssertion.assertThat(captor.getValue())
                .hasHotelRoomIdEqualTo(HOTEL_ROOM_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, START_YEAR_LATER);
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
