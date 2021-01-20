package info.pionas.rental.domain.hotelroom;

/**
 *
 * @author Adi
 */
public interface HotelRoomRepository {

    void save(HotelRoom hotelRoom);

    HotelRoom findById(String id);
}
