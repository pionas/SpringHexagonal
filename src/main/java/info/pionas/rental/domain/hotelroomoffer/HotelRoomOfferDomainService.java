package info.pionas.rental.domain.hotelroomoffer;

import info.pionas.rental.domain.hotel.Hotel;

import static info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer.Builder.hotelRoomOffer;

public class HotelRoomOfferDomainService {
    public HotelRoomOffer createOfferForHotelRoom(Hotel hotel, CreateHotelRoomOffer createHotelRoomOffer) {
        if (hotel.hasRoomWithNumber(createHotelRoomOffer.getNumber())) {
            return hotelRoomOffer()
                    .withHotelId(createHotelRoomOffer.getHotelId())
                    .withHotelRoomNumber(createHotelRoomOffer.getNumber())
                    .withPrice(createHotelRoomOffer.getPrice())
                    .withAvailability(createHotelRoomOffer.getStart(), createHotelRoomOffer.getEnd())
                    .build();
        } else {
            throw new HotelRoomNotFoundException(createHotelRoomOffer.getHotelId(), createHotelRoomOffer.getNumber());
        }
    }
}
