package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferDomainService;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HotelRoomOfferApplicationServiceFactory {
    @Bean
    HotelRoomOfferApplicationService create(HotelRoomOfferRepository repository, HotelRepository hotelRepository) {
        return new HotelRoomOfferApplicationService(repository, hotelRepository, new HotelRoomOfferDomainService());
    }
}
