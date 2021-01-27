package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomRepistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HotelRoomOfferApplicationService {
    private final HotelRoomRepistory hotelRoomRepistory;

    public void add(HotelRoomOffertDto dto) {
//        hotelRoomRepistory.save(dto);
    }
}
