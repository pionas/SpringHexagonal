package info.pionas.rental.query.hotel;

import static java.util.Collections.emptyList;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
public class QueryHotelRepository {

    private final SpringJpaQueryHotelRepository springJpaQueryHotelRepository;

    public Iterable<HotelReadModel> findAll() {
//        return springJpaQueryHotelRepository.findAll();
        return emptyList();
    }
}
