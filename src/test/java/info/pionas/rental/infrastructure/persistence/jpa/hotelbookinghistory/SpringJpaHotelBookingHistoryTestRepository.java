package info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaHotelBookingHistoryTestRepository {
    private final SpringJpaHotelBookingHistoryRepository repository;

    public void deleteById(String hotelRoomId) {
        repository.deleteById(hotelRoomId);
    }
}
