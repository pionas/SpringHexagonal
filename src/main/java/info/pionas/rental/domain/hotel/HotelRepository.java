package info.pionas.rental.domain.hotel;

import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository {

    String save(Hotel hotel);

    Hotel findById(String id);
}
