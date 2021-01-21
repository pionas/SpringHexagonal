package info.pionas.rental.infrastructure.rest.api.apartment;

import info.pionas.rental.application.apartment.ApartmentApplicationService;
import info.pionas.rental.query.apartment.ApartmentDetails;
import info.pionas.rental.query.apartment.ApartmentReadModel;
import info.pionas.rental.query.apartment.QueryApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/apartment")
public class ApartmentRestController {

    private final ApartmentApplicationService apartmentApplicationService;
    private final QueryApartmentRepository queryApartmentRepository;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody ApartmentDto apartmentDto) {
        String apartmentId = apartmentApplicationService.add(
                apartmentDto.getOwnerId(),
                apartmentDto.getStreet(),
                apartmentDto.getPostalCode(),
                apartmentDto.getHouseNumber(),
                apartmentDto.getApartmentNumber(),
                apartmentDto.getCity(),
                apartmentDto.getCountry(),
                apartmentDto.getDescription(),
                apartmentDto.getRoomsDefinition()
        );
        return ResponseEntity.created(URI.create("/apartment/" + apartmentId)).build();
    }

    @PutMapping("/book/{id}")
    public void book(@PathVariable String id, @RequestBody ApartmentBookingDto apartmentBookingDto) {
        apartmentApplicationService.book(
                id,
                apartmentBookingDto.getTenentId(),
                apartmentBookingDto.getStart(),
                apartmentBookingDto.getEnd()
        );
    }

    @GetMapping
    public Iterable<ApartmentReadModel> findAll() {
        return queryApartmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ApartmentDetails findById(@PathVariable String id) {
        return queryApartmentRepository.findById(id);
    }
}
