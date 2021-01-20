package info.pionas.rental.infrastructure.rest.api.hotelroom;

import info.pionas.rental.application.hotelroom.HotelRoomApplicationService;
import info.pionas.rental.query.hotelroom.HotelRoomReadModel;
import info.pionas.rental.query.hotelroom.QueryHotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
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
