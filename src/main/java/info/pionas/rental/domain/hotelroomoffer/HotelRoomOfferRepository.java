package info.pionas.rental.domain.hotelroomoffer;

public interface HotelRoomOfferRepository {
    String save(HotelRoomOffer hotelRoomOffer);

    HotelRoomOffer findById(String id);
}
