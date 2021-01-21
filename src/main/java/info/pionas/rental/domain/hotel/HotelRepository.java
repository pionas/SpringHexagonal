package info.pionas.rental.domain.hotel;

/**
 * @author Adi
 */
public interface HotelRepository {

    String save(Hotel hotel);

    Hotel findById(String id);
}
