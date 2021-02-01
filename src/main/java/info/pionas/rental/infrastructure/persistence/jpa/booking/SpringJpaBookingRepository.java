package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.RentalType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
interface SpringJpaBookingRepository extends CrudRepository<Booking, UUID> {

    List<Booking> findAllByRentalTypeAndRentalPlaceId(RentalType rentalType, String rentalPlaceId);
}
