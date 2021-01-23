package info.pionas.rental.infrastructure.rest.api.hotel;

import info.pionas.rental.application.hotel.HotelApplicationService;
import info.pionas.rental.application.hotel.HotelDto;
import info.pionas.rental.query.hotel.HotelReadModel;
import info.pionas.rental.query.hotel.QueryHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RequiredArgsConstructor
@RestController
@RequestMapping("/hotel")
public class HotelRestController {

    private final HotelApplicationService hotelApplicationService;
    private final QueryHotelRepository queryHotelRepository;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody HotelDto hotelDto) {
        String hotelId = hotelApplicationService.add(
                hotelDto.getName(),
                hotelDto.getStreet(),
                hotelDto.getPostalCode(),
                hotelDto.getBuildingNumber(),
                hotelDto.getCity(),
                hotelDto.getCountry()
        );
        return ResponseEntity.created(URI.create("/hotel/" + hotelId)).build();
    }

    @GetMapping
    public Iterable<HotelReadModel> findAll() {
        return queryHotelRepository.findAll();
    }
}
