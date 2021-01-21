package info.pionas.rental.infrastructure.rest.api.hotelroom;

import info.pionas.rental.application.hotelroom.HotelRoomApplicationService;
import info.pionas.rental.query.hotelroom.HotelRoomReadModel;
import info.pionas.rental.query.hotelroom.QueryHotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotelroom")
public class HotelRoomRestController {

    private final HotelRoomApplicationService hotelRoomApplicationService;
    private final QueryHotelRoomRepository queryHotelRoomRepository;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody HotelRoomDto hotelRoomDto) {
        String id = hotelRoomApplicationService.add(
                hotelRoomDto.getHotelId(),
                hotelRoomDto.getNumber(),
                hotelRoomDto.getSpacesDefinition(),
                hotelRoomDto.getDescription()
        );
        return ResponseEntity.created(URI.create("/hotelroom/" + id)).build();
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<String> book(@PathVariable String id, @RequestBody HotelRoomBookingDto hotelRoomBookingDto) {
        String bookingId = hotelRoomApplicationService.book(
                id,
                hotelRoomBookingDto.getTenentId(),
                hotelRoomBookingDto.getDays()
        );

        return ResponseEntity.created(URI.create("/booking/" + bookingId)).build();
    }

    @GetMapping("/hotel/{hotelId}")
    public Iterable<HotelRoomReadModel> findAll(@PathVariable String hotelId) {
        return queryHotelRoomRepository.findAll(hotelId);
    }
}
