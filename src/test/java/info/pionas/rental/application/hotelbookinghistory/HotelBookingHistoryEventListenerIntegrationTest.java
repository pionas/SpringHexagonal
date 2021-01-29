package info.pionas.rental.application.hotelbookinghistory;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.hotelroom.HotelRoomApplicationService;
import info.pionas.rental.application.hotelroom.HotelRoomBookingDto;
import info.pionas.rental.domain.hotel.HotelRoom;
import info.pionas.rental.domain.hotel.HotelRoomFactory;
import info.pionas.rental.domain.hotel.HotelRoomRepository;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryAssertion;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory.SpringJpaHotelBookingHistoryTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotelroom.SpringJpaHotelRoomTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Arrays.asList;

@SpringBootTest
@Tag("IntegrationTest")
class HotelBookingHistoryEventListenerIntegrationTest {
    private static final UUID HOTEL_ID = UUID.randomUUID();
    private static final int HOTEL_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";

    @Autowired
    private HotelRoomApplicationService hotelRoomApplicationService;
    @Autowired
    private HotelBookingHistoryRepository hotelBookingHistoryRepository;
    @Autowired
    private SpringJpaHotelBookingHistoryTestRepository springJpaHotelBookingHistoryTestRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private SpringJpaHotelRoomTestRepository springJpaHotelRoomTestRepository;

    private String hotelRoomId;

    @AfterEach
    void removeHotelRoom() {
        springJpaHotelRoomTestRepository.deleteById(hotelRoomId);
        springJpaHotelBookingHistoryTestRepository.deleteById(HOTEL_ID.toString());
    }

    @Test
    @Transactional
    void shouldUpdateHotelBookingHistory() {
        String tenantId = "11223344";
        List<LocalDate> days = asList(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 1, 14));
        givenExistingHotelRoom();
        HotelRoomBookingDto hotelRoomBookingDto = new HotelRoomBookingDto(HOTEL_ID.toString(), HOTEL_NUMBER, hotelRoomId, tenantId, days);

        hotelRoomApplicationService.book(hotelRoomBookingDto);
        HotelBookingHistory actual = hotelBookingHistoryRepository.findFor(HOTEL_ID.toString());

        HotelBookingHistoryAssertion.assertThat(actual).hasHotelRoomBookingHistoryFor(hotelRoomId, tenantId, days);
    }

    private void givenExistingHotelRoom() {
        hotelRoomId = hotelRoomRepository.save(createHotelRoom());
    }

    private HotelRoom createHotelRoom() {
        return new HotelRoomFactory().create(HOTEL_ID, HOTEL_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }
}