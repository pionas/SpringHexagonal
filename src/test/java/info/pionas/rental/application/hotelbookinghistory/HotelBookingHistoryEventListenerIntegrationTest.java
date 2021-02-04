package info.pionas.rental.application.hotelbookinghistory;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.hotel.HotelApplicationService;
import info.pionas.rental.application.hotel.HotelRoomBookingDto;
import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryAssertion;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotel.SpringJpaHotelTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory.SpringJpaHotelBookingHistoryTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static java.util.Arrays.asList;

@SpringBootTest
@Tag("IntegrationTest")
class HotelBookingHistoryEventListenerIntegrationTest {
    private static final int HOTEL_ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";

    @Autowired
    private HotelApplicationService hotelApplicationService;
    @Autowired
    private HotelBookingHistoryRepository hotelBookingHistoryRepository;
    @Autowired
    private SpringJpaHotelBookingHistoryTestRepository springJpaHotelBookingHistoryTestRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private SpringJpaHotelTestRepository springJpaHotelTestRepository;

    private String hotelId;

    @BeforeEach
    void givenExistingHotelRoom() {
        Hotel hotel = hotel().withName("Great hotel").build();
        hotelId = hotelRepository.save(hotel);

        hotel.addRoom(HOTEL_ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
        hotelRepository.save(hotel);
    }

    @AfterEach
    void removeHotelRoom() {
        springJpaHotelTestRepository.deleteById(hotelId);
        springJpaHotelBookingHistoryTestRepository.deleteById(hotelId);
    }

    @Test
    @Transactional
    void shouldUpdateHotelBookingHistory() {
        String tenantId = "11223344";
        List<LocalDate> days = asList(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 1, 14));
        HotelRoomBookingDto hotelRoomBookingDto = new HotelRoomBookingDto(hotelId, HOTEL_ROOM_NUMBER, tenantId, days);

        hotelApplicationService.book(hotelRoomBookingDto);
        HotelBookingHistory actual = hotelBookingHistoryRepository.findFor(hotelId);

        HotelBookingHistoryAssertion.assertThat(actual).hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER, tenantId, days);
    }
}