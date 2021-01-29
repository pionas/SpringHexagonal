package info.pionas.rental.domain.apartmentbookinghistory;

import info.pionas.rental.domain.period.Period;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "APARTMENT_BOOKING_HISTORY")
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApartmentBookingHistory {

    @Id
    private String apartmentId;
    @ElementCollection
    @CollectionTable(name = "APARTMENT_BOOKING", joinColumns = @JoinColumn(name = "APARTMENT_ID"))
    private List<ApartmentBooking> bookings = new ArrayList<>();

    public ApartmentBookingHistory(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public void addBookingStart(LocalDateTime eventCreationDateTime, String ownerId, String tenantId, Period period) {
        add(
                ApartmentBooking.start(
                        eventCreationDateTime,
                        ownerId,
                        tenantId,
                        period
                )
        );
    }

    private void add(ApartmentBooking apartmentBooking) {
        bookings.add(apartmentBooking);
    }

}
