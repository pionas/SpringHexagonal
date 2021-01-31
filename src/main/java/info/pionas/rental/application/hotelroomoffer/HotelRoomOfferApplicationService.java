package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferDomainService;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HotelRoomOfferApplicationService {
    private final HotelRoomOfferRepository hotelRoomOfferRepository;
    private final HotelRepository hotelRepository;

    public String add(HotelRoomOfferDto dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId());
        HotelRoomOffer hotelRoomOffer = new HotelRoomOfferDomainService().createOfferForHotelRoom(hotel, dto.asDto());
        return hotelRoomOfferRepository.save(hotelRoomOffer);
    }
}
