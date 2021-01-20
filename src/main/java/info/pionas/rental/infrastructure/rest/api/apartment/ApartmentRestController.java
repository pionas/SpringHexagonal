package info.pionas.rental.infrastructure.rest.api.apartment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import info.pionas.rental.application.apartment.ApartmentApplicationService;
import info.pionas.rental.query.apartment.ApartmentDetails;
import info.pionas.rental.query.apartment.ApartmentReadModel;
import info.pionas.rental.query.apartment.QueryApartmentRepository;

/**
 *
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
