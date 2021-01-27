package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomNotFoundException;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HotelRoomOfferApplicationService {
    private final HotelRoomOfferRepository hotelRoomOfferRepository;
    private final HotelRoomRepository hotelRoomRepository;

    public String add(HotelRoomOffertDto dto) {
        if (!hotelRoomRepository.existById(dto.getHotelRoomId())) {
            throw new HotelRoomNotFoundException(dto.getHotelRoomId());
        }
        HotelRoomOffer hotelRoomOffer = HotelRoomOffer.Builder.hotelRoomOffer()
                .withHotelRoomId(dto.getHotelRoomId())
                .withPrice(dto.getPrice())
                .withAvailability(dto.getStart(), dto.getEnd())
                .build();
        return hotelRoomOfferRepository.save(hotelRoomOffer);
    }
}
