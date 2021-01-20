package info.pionas.rental.query.hotelroom;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Adi
 */
interface SpringJpaQueryHotelRoomRepository 
//        extends CrudRepository<HotelRoomReadModel, String> 
{

    public Iterable<HotelRoomReadModel> findAllByHotelId(String hotelId);

}
