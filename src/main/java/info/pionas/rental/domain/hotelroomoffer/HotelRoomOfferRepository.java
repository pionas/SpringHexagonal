package info.pionas.rental.domain.hotelroomoffer;

import java.util.UUID;

public interface HotelRoomOfferRepository {
    UUID save(HotelRoomOffer hotelRoomOffer);
}
