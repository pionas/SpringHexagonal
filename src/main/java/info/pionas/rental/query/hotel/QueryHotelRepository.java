package info.pionas.rental.query.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
@RequiredArgsConstructor
public class QueryHotelRepository {

    private final SpringJpaQueryHotelRepository springJpaQueryHotelRepository;

    public Iterable<HotelReadModel> findAll() {
        return springJpaQueryHotelRepository.findAll();
    }
}
