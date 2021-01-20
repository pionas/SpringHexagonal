package info.pionas.rental.domain.apartmentbookinghistory;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "APARTMENT_BOOKING_HISTORY")
public class ApartmentBookingHistory {

    @Id
    @GeneratedValue
    private final String apartmentId;
    @OneToMany
    private final List<ApartmentBooking> bookings = new ArrayList<>();

    public void add(ApartmentBooking apartmentBooking) {
        bookings.add(apartmentBooking);
    }

}
