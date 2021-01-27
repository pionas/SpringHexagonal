package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaHotelTestRepository {
    private final SpringJpaHotelRepository repository;

    public void deleteById(String hotelId) {
        repository.deleteById(UUID.fromString(hotelId));
    }

    public void deleteAll(List<String> hotelIds) {
        hotelIds.forEach(this::deleteById);
    }

    public String save(Hotel hotel) {
        return repository.save(hotel).id();
    }
}