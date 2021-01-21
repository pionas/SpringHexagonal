package info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryAssertion;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaHotelBookingHistoryRepositoryIntegrationTest {
    @Autowired
    private HotelBookingHistoryRepository hotelBookingHistoryRepository;

    @Test
    void shouldRecognizeApartmentBookingHistoryDoesNotExist() {
        String id = randomId();

        assertThat(hotelBookingHistoryRepository.existFor(id)).isFalse();
    }

    @Test
    void shouldRecognizeApartmentBookingHistoryExist() {
        String id = randomId();
        hotelBookingHistoryRepository.save(new HotelBookingHistory(id));
        assertThat(hotelBookingHistoryRepository.existFor(id)).isTrue();
    }

    @Test
    @Transactional
    void shouldFindExistingHotelBookingHistory() {
        String hotelId = randomId();
        String hotelRoomId = randomId();
        LocalDateTime bookingDateTime = LocalDateTime.now();
        String tenantId = "123";
        List<LocalDate> days = asList(LocalDate.now(), LocalDate.now().plusDays(1));
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(hotelId);

        hotelBookingHistory.add(hotelRoomId, bookingDateTime, tenantId, days);
        hotelBookingHistoryRepository.save(hotelBookingHistory);
        HotelBookingHistory actual = hotelBookingHistoryRepository.findFor(hotelId);

        HotelBookingHistoryAssertion
                .assertThat(actual).hasHotelRoomBookingHistoryFor(hotelRoomId, bookingDateTime, tenantId, days);
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }


}