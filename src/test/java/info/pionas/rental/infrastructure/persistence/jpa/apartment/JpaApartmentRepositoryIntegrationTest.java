package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.ApartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class JpaApartmentRepositoryIntegrationTest {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    void shoudlThrowExceptionWhenApartmentDoesNotExist() {
        ApartmentDoesNotExistException actual = assertThrows(ApartmentDoesNotExistException.class, () -> apartmentRepository.findById("1234"));
        assertThat(actual).hasMessage("Apartment with id 1234 does not exist");
    }
}