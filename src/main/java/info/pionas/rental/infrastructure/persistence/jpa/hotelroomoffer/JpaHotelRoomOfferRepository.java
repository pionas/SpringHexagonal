package info.pionas.rental.infrastructure.persistence.jpa.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
class JpaHotelRoomOfferRepository implements HotelRoomOfferRepository {
    private final SpringJpaHotelRoomOfferRepository repository;

    @Override
    public UUID save(HotelRoomOffer hotelRoomOffer) {
        return repository.save(hotelRoomOffer).id();
    }
}
