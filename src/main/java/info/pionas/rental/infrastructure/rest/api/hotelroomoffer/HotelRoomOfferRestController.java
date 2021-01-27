package info.pionas.rental.infrastructure.rest.api.hotelroomoffer;

import info.pionas.rental.application.hotelroomoffer.HotelRoomOfferApplicationService;
import info.pionas.rental.application.hotelroomoffer.HotelRoomOffertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelroomoffer")
public class HotelRoomOfferRestController {
    private final HotelRoomOfferApplicationService hotelRoomOfferApplicationService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody HotelRoomOffertDto hotelRoomOffertDto) {
        String hotelRoomOfferId = hotelRoomOfferApplicationService.add(hotelRoomOffertDto);
        return ResponseEntity.created(URI.create("/hotelroomoffer/" + hotelRoomOfferId)).build();
    }
}
