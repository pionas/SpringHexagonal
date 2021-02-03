package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferFactory;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentOfferApplicationServiceFactory {
    @Bean
    public ApartmentOfferApplicationService apartmentOfferApplicationService(ApartmentRepository apartmentRepository, ApartmentOfferRepository apartmentOfferRepository) {
        ApartmentOfferFactory apartmentOfferFactory = new ApartmentOfferFactory(apartmentRepository);
        return new ApartmentOfferApplicationService(apartmentOfferRepository, apartmentOfferFactory);
    }
}
