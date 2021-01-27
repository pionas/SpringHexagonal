package info.pionas.rental.infrastructure.persistence.jpa.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaHotelRoomOfferRepository implements HotelRoomOfferRepository {

    private final SpringJpaHotelRoomOfferRepository hotelRoomOfferRepository;

    @Override
    public String save(HotelRoomOffer hotelRoomOffer) {
        return hotelRoomOfferRepository.save(hotelRoomOffer).id();
    }

    @Override
    public HotelRoomOffer findById(String id) {
        return hotelRoomOfferRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new HotelRoomOfferDoesNotExistException(id));
    }

}
