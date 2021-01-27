package info.pionas.rental.infrastructure.persistence.jpa.hotelroomoffer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaHotelRoomOfferTestRepository {
    private final SpringJpaHotelRoomOfferRepository repository;

    public void deleteById(String hotelRoomOfferId) {
        repository.deleteById(UUID.fromString(hotelRoomOfferId));
    }

    public void deleteAll(List<String> hotelRoomOfferIds) {
        hotelRoomOfferIds.forEach(this::deleteById);
    }
}