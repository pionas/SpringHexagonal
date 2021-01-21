package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpringJpaHotelTestRepository {
    private final SpringJpaHotelRepository repository;

    SpringJpaHotelTestRepository(SpringJpaHotelRepository repository) {
        this.repository = repository;
    }

    public void deleteById(String hotelId) {
        repository.deleteById(UUID.fromString(hotelId));
    }

    public void deleteAll(List<String> hotelIds) {
        hotelIds.forEach(this::deleteById);
    }
}