package info.pionas.rental.query.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adi
 */
@AllArgsConstructor
@Getter
@Entity
@Table(name = "APARTMENT_BOOKING_HISTORY")
public class ApartmentBookingHistoryReadModel {

    @Id
    private String apartmentId;
    @ElementCollection
    @CollectionTable(name = "APARTMENT_BOOKING", joinColumns = @JoinColumn(name = "APARTMENT_ID"))
    private List<ApartmentBookingReadModel> bookings = new ArrayList<>();
}
