package info.pionas.rental.infrastructure.rest.api.apartment;

import info.pionas.rental.application.apartment.ApartmentApplicationService;
import info.pionas.rental.query.apartment.ApartmentDetails;
import info.pionas.rental.query.apartment.ApartmentReadModel;
import info.pionas.rental.query.apartment.QueryApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@RestController
@RequestMapping("/apartment")
public class ApartmentRestController {

    private final ApartmentApplicationService apartmentApplicationService;
    private final QueryApartmentRepository queryApartmentRepository;

    @PostMapping
    public void add(@RequestBody ApartmentDto apartmentDto) {
        apartmentApplicationService.add(
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
