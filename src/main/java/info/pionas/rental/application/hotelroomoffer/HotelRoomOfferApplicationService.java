package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomRepistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class HotelRoomOfferApplicationService {
    private final HotelRoomRepistory hotelRoomRepistory;

    void add(HotelRoomOffertDto dto) {
        HotelRoomOffer hotelRoomOffer = HotelRoomOffer.Builder.hotelRoomOffer()
                .withHotelRoomId(dto.getHotelRoomId())
                .withPrice(dto.getPrice())
                .witAvailability(dto.getStart(), dto.getEnd())
                .build();
        hotelRoomRepistory.save(hotelRoomOffer);
    }
}
