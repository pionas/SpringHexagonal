package info.pionas.rental.infrastructure.persistence.jpa.hotelroom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaHotelRoomTestRepository {
    private final SpringJpaHotelRoomRepository repository;

    public void deleteById(String hotelRoomId) {
        repository.deleteById(UUID.fromString(hotelRoomId));
    }

    public void deleteAll(List<String> ids) {
        ids.forEach(this::deleteById);
    }
}