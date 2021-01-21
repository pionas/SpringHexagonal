package info.pionas.rental.query.hotelroom;

/**
 * @author Adi
 */
interface SpringJpaQueryHotelRoomRepository
//        extends CrudRepository<HotelRoomReadModel, String> 
{

    public Iterable<HotelRoomReadModel> findAllByHotelId(String hotelId);

}
