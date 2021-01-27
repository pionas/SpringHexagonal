package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomNotFoundException;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class HotelRoomOfferApplicationService {
    private final HotelRoomOfferRepository hotelRoomOfferRepository;
    private final HotelRoomRepository hotelRoomRepository;

    void add(HotelRoomOffertDto dto) {
        if (!hotelRoomRepository.existById(dto.getHotelRoomId())) {
            throw new HotelRoomNotFoundException(dto.getHotelRoomId());
        }
        HotelRoomOffer hotelRoomOffer = HotelRoomOffer.Builder.hotelRoomOffer()
                .withHotelRoomId(dto.getHotelRoomId())
                .withPrice(dto.getPrice())
                .withAvailability(dto.getStart(), dto.getEnd())
                .build();
        hotelRoomOfferRepository.save(hotelRoomOffer);
    }
}
