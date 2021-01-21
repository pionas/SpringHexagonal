package info.pionas.rental.infrastructure.rest.api.hotelroom;

import info.pionas.rental.application.hotelroom.HotelRoomApplicationService;
import info.pionas.rental.query.hotelroom.HotelRoomReadModel;
import info.pionas.rental.query.hotelroom.QueryHotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@RestController
@RequestMapping("/hotelroom")
public class HotelRoomRestController {

    private final HotelRoomApplicationService hotelRoomApplicationService;
    private final QueryHotelRoomRepository queryHotelRoomRepository;

    @PostMapping
    public void add(@RequestBody HotelRoomDto hotelRoomDto) {
        hotelRoomApplicationService.add(
                hotelRoomDto.getHotelId(),
                hotelRoomDto.getNumber(),
                hotelRoomDto.getSpacesDefinition(),
                hotelRoomDto.getDescription()
        );
    }

    @PutMapping("/book/{id}")
    public void book(@PathVariable String id, @RequestBody HotelRoomBookingDto hotelRoomBookingDto) {
        hotelRoomApplicationService.book(
                id,
                hotelRoomBookingDto.getTenentId(),
                hotelRoomBookingDto.getDays()
        );
    }

    @GetMapping("/hotel/{hotelId}")
    public Iterable<HotelRoomReadModel> findAll(@PathVariable String hotelId) {
        return queryHotelRoomRepository.findAll(hotelId);
    }
}
