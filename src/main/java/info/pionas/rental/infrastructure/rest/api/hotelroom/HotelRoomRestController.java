package info.pionas.rental.infrastructure.rest.api.hotelroom;

import info.pionas.rental.application.hotel.HotelApplicationService;
import info.pionas.rental.application.hotel.HotelRoomBookingDto;
import info.pionas.rental.application.hotel.HotelRoomDto;
import info.pionas.rental.query.hotelroom.HotelRoomReadModel;
import info.pionas.rental.query.hotelroom.QueryHotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelroom")
public class HotelRoomRestController {
    private final HotelApplicationService hotelApplicationService;
    private final QueryHotelRoomRepository queryHotelRoomRepository;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody HotelRoomDto hotelRoomDto) {
        hotelApplicationService.add(hotelRoomDto);
        URI uri = URI.create("/hotelroom/" + hotelRoomDto.getHotelId() + "/" + hotelRoomDto.getNumber());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/book")
    public ResponseEntity<String> book(@RequestBody HotelRoomBookingDto hotelRoomBookingDto) {
        UUID bookingId = hotelApplicationService.book(hotelRoomBookingDto);

        return ResponseEntity.created(URI.create("/booking/" + bookingId)).build();
    }

    @GetMapping("/hotel/{hotelId}")
    public Iterable<HotelRoomReadModel> findAll(@PathVariable String hotelId) {
        return queryHotelRoomRepository.findAll(hotelId);
    }
}
