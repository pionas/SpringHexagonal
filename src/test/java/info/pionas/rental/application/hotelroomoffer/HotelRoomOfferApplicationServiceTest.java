package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomRepistory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class HotelRoomOfferApplicationServiceTest {
    private static final String HOTEL_ROOM_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2020, 12, 10);
    private static final LocalDate END = LocalDate.of(2021, 12, 20);
    private final HotelRoomRepistory repository = mock(HotelRoomRepistory.class);
    private final HotelRoomOfferApplicationService service = new HotelRoomOfferApplicationService(repository);

    @Test
    void shouldCreateHotelRoomOffer() {
        ArgumentCaptor<HotelRoomOffer> captor = ArgumentCaptor.forClass(HotelRoomOffer.class);
        HotelRoomOffertDto dto = new HotelRoomOffertDto(HOTEL_ROOM_ID, PRICE, START, END);
        givenExistingHotelRoom();

        service.add(dto);

        then(repository).should().save(captor.capture());
        HotelRoomOfferAssertion.assertThat(captor.getValue())
                .hasHotelRoomIdEqualTo(HOTEL_ROOM_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    private void givenExistingHotelRoom() {

    }
}
