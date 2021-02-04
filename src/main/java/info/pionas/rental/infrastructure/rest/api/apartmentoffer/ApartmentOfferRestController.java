package info.pionas.rental.infrastructure.rest.api.apartmentoffer;

import info.pionas.rental.application.apartmentoffer.ApartmentOfferApplicationService;
import info.pionas.rental.application.apartmentoffer.ApartmentOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apartmentoffer")
public class ApartmentOfferRestController {
    private final ApartmentOfferApplicationService apartmentOfferApplicationService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody ApartmentOfferDto apartmentOfferDto) {
        UUID apartmentOfferId = apartmentOfferApplicationService.add(apartmentOfferDto);
        return ResponseEntity.created(URI.create("/apartmentoffer/" + apartmentOfferId)).build();
    }
}
