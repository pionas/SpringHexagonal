package info.pionas.rental.domain.hotelroomoffer;

import info.pionas.rental.domain.hotel.Hotel;

public class HotelRoomOfferDomainService {
    public HotelRoomOffer createOfferForHotelRoom(Hotel hotel, CreateHotelRoomOffer createHotelRoomOffer) {
        if (hotel.hasRoomWithNumber(createHotelRoomOffer.getNumber())) {
            return HotelRoomOffer.Builder.hotelRoomOffer()
                    .withHotelRoomId(createHotelRoomOffer.getHotelRoomId())
                    .withPrice(createHotelRoomOffer.getPrice())
                    .withAvailability(createHotelRoomOffer.getStart(), createHotelRoomOffer.getEnd())
                    .build();

        }
        throw HotelRoomNotFoundException.ofHotelRoomId(createHotelRoomOffer.getHotelRoomId());

    }
}
