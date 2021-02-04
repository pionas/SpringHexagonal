package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferDomainService;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class HotelRoomOfferApplicationService {
    private final HotelRoomOfferRepository hotelRoomOfferRepository;
    private final HotelRepository hotelRepository;
    private final HotelRoomOfferDomainService hotelRoomOfferDomainService;

    public UUID add(HotelRoomOfferDto dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId());
        HotelRoomOffer hotelRoomOffer = hotelRoomOfferDomainService.createOfferForHotelRoom(hotel, dto.asDto());
        return hotelRoomOfferRepository.save(hotelRoomOffer);
    }
}
