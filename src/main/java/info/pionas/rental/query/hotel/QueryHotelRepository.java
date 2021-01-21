package info.pionas.rental.query.hotel;

import lombok.RequiredArgsConstructor;

import static java.util.Collections.emptyList;

/**
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
