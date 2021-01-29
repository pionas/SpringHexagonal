package info.pionas.rental.application.hotelbookinghistory;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryAssertion;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import info.pionas.rental.domain.hotel.HotelRoomBookedTestFactory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class HotelBookingHistoryEventListenerTest {
    private static final String EVENT_ID = "eventId123";
    private static final LocalDateTime EVENT_CREATION_DATE_TIME = LocalDateTime.now();
    private static final String HOTEL_ID = "hotelId1";
    private static final String HOTEL_ROOM_ID = "hotelRoomId1";
    private static final String TENANT_ID = "tenantId1";
    private static final List<LocalDate> DAYS = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8));

    private final HotelBookingHistoryRepository repository = mock(HotelBookingHistoryRepository.class);
    private final HotelBookingHistoryEventListener eventListener = new HotelBookingHistoryEventListener(repository);
    private final ArgumentCaptor<HotelBookingHistory> captor = ArgumentCaptor.forClass(HotelBookingHistory.class);

    @Test
    void shouldAddNewHotelBookingHistory() {
        given(repository.existsFor(HOTEL_ID)).willReturn(false);

        eventListener.consume(HotelRoomBookedTestFactory.create(EVENT_ID, EVENT_CREATION_DATE_TIME, HOTEL_ROOM_ID, HOTEL_ID, TENANT_ID, DAYS));

        then(repository).should().save(captor.capture());
        HotelBookingHistoryAssertion.assertThat(captor.getValue())
                .hasInformationAboutHistoryOfHotelRooms(1)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_ID, 1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, TENANT_ID, DAYS);
    }

    @Test
    void shouldUpdateExistingHotelBookingHistory() {
        String tenantId2 = "tenantId2";
        String hotelRoomId2 = "hotelRoomId2";
        List<LocalDate> days2 = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8), LocalDate.of(2020, 9, 10));
        givenExistingHotelBookingHistory();

        eventListener.consume(HotelRoomBookedTestFactory.create(EVENT_ID, EVENT_CREATION_DATE_TIME, hotelRoomId2, HOTEL_ID, tenantId2, days2));

        then(repository).should().save(captor.capture());
        HotelBookingHistoryAssertion.assertThat(captor.getValue())
                .hasInformationAboutHistoryOfHotelRooms(2)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_ID, 1)
                .hasInformationAboutHistoryOfHotelRoom(hotelRoomId2, 1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, TENANT_ID, DAYS)
                .hasHotelRoomBookingHistoryFor(hotelRoomId2, tenantId2, days2);
    }

    private void givenExistingHotelBookingHistory() {
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID);
        hotelBookingHistory.add(HOTEL_ROOM_ID, LocalDateTime.now(), TENANT_ID, DAYS);
        given(repository.existsFor(HOTEL_ID)).willReturn(true);
        given(repository.findFor(HOTEL_ID)).willReturn(hotelBookingHistory);
    }
}