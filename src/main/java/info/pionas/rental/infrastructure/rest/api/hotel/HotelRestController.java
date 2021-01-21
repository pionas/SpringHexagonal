package info.pionas.rental.infrastructure.rest.api.hotel;

import info.pionas.rental.application.hotel.HotelApplicationService;
import info.pionas.rental.query.hotel.HotelReadModel;
import info.pionas.rental.query.hotel.QueryHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@RestController
@RequestMapping("/hotel")
public class HotelRestController {

    private final HotelApplicationService hotelApplicationService;
    private final QueryHotelRepository queryHotelRepository;

    @PostMapping
    public void add(@RequestBody HotelDto hotelDto) {
        hotelApplicationService.add(
                hotelDto.getName(),
                hotelDto.getStreet(),
                hotelDto.getPostalCode(),
                hotelDto.getBuildingNumber(),
                hotelDto.getCity(),
                hotelDto.getCountry()
        );
    }

    @GetMapping
    public Iterable<HotelReadModel> findAll() {
        return queryHotelRepository.findAll();
    }
}
