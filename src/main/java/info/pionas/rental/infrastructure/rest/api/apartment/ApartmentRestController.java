package info.pionas.rental.infrastructure.rest.api.apartment;

import info.pionas.rental.application.apartment.ApartmentApplicationService;
import info.pionas.rental.application.apartment.ApartmentBookingDto;
import info.pionas.rental.application.apartment.ApartmentDto;
import info.pionas.rental.query.apartment.ApartmentDetails;
import info.pionas.rental.query.apartment.ApartmentReadModel;
import info.pionas.rental.query.apartment.QueryApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/apartment")
public class ApartmentRestController {

    private final ApartmentApplicationService apartmentApplicationService;
    private final QueryApartmentRepository queryApartmentRepository;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody ApartmentDto apartmentDto) {
        String apartmentId = apartmentApplicationService.add(apartmentDto);
        return ResponseEntity.created(URI.create("/apartment/" + apartmentId)).build();
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<String> book(@RequestBody ApartmentBookingDto apartmentBookingDto) {
        UUID bookingId = apartmentApplicationService.book(apartmentBookingDto);

        return ResponseEntity.created(URI.create("/booking/" + bookingId)).build();
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
