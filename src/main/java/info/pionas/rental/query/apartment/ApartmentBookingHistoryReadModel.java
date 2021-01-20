package info.pionas.rental.query.apartment;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Getter
@Table(name = "APARTMENT_BOOKING_HISTORY")
public class ApartmentBookingHistoryReadModel {

    @Id
    private final String apartmentId;
    @OneToMany
    private final List<ApartmentBookingReadModel> bookings = new ArrayList<>();
}
