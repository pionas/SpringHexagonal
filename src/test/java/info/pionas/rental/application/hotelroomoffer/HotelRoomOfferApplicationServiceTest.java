package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import info.pionas.rental.domain.hotelroomoffer.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class HotelRoomOfferApplicationServiceTest {
    private static final String HOTEL_ROOM_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 12, 10);
    private static final LocalDate END = LocalDate.of(2041, 12, 20);
    private final HotelRoomRepository hotelRoomRepository = mock(HotelRoomRepository.class);
    private final HotelRoomOfferRepository repository = mock(HotelRoomOfferRepository.class);
    private final HotelRoomOfferApplicationService service = new HotelRoomOfferApplicationService(repository, hotelRoomRepository);

    @Test
    void shouldCreateHotelRoomOffer() {
        ArgumentCaptor<HotelRoomOffer> captor = ArgumentCaptor.forClass(HotelRoomOffer.class);
        givenExistingHotelRoom();

        service.add(getHotelRoomOfferDto());

        then(repository).should().save(captor.capture());
        HotelRoomOfferAssertion.assertThat(captor.getValue())
                .hasHotelRoomIdEqualTo(HOTEL_ROOM_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    void shouldRecognizeHotelRoomDoesNotExist() {
        givenNotExistingHotelRoom();

        HotelRoomNotFoundException actual = assertThrows(HotelRoomNotFoundException.class, () -> {
            service.add(getHotelRoomOfferDto());
        });
        assertThat(actual).hasMessage("Hotel room with id " + HOTEL_ROOM_ID + " does not exist");
    }

    @Test
    void shouldRecognizePriceIsNotHigherThanZero() {
        givenExistingHotelRoom();
        HotelRoomOffertDto dto = new HotelRoomOffertDto(HOTEL_ROOM_ID, BigDecimal.ZERO, START, END);
        NotAllowedMoneyValueException actual = assertThrows(NotAllowedMoneyValueException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Price 0 is not greater than zero");
    }

    @Test
    void shouldRecognizeAvailabilityStartIsAfterEnd() {
        givenExistingHotelRoom();
        HotelRoomOffertDto dto = new HotelRoomOffertDto(HOTEL_ROOM_ID, PRICE, END, START);
        HotelRoomAvailabilityException actual = assertThrows(HotelRoomAvailabilityException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Start date: 2041-12-20 of availability is after end date: 2040-12-10");
    }

    private void givenExistingHotelRoom() {
        given(hotelRoomRepository.existById(HOTEL_ROOM_ID)).willReturn(true);
    }

    private void givenNotExistingHotelRoom() {
        given(hotelRoomRepository.existById(HOTEL_ROOM_ID)).willReturn(false);
    }

    private HotelRoomOffertDto getHotelRoomOfferDto() {
        return new HotelRoomOffertDto(HOTEL_ROOM_ID, PRICE, START, END);
    }

}
