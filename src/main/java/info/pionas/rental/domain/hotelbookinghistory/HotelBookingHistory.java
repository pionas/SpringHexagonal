package info.pionas.rental.domain.hotelbookinghistory;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Entity(name = "HOTEL_BOOKING_HISTORY")
@SuppressWarnings("PMD.UnusedPrivateField")
public class HotelBookingHistory {

    @Id
    private String hotelId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<HotelRoomBookingHistory> hotelRoomBookingHistories = new ArrayList<>();

    public HotelBookingHistory(String hotelId) {
        this.hotelId = hotelId;
    }

    public void add(int hotelRoomNumber, LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        HotelRoomBookingHistory hotelRoomBookingHistory = findFor(hotelRoomNumber);
        hotelRoomBookingHistory.add(bookingDateTime, tenantId, days);
    }

    private HotelRoomBookingHistory findFor(int hotelRoomNumber) {
        Optional<HotelRoomBookingHistory> history = hotelRoomBookingHistories.stream()
                .filter(hotelRoomBookingHistory -> hotelRoomBookingHistory.hasNumberEqualTo(hotelRoomNumber))
                .findFirst();

        if (history.isEmpty()) {
            HotelRoomBookingHistory hotelRoomBookingHistory = new HotelRoomBookingHistory(hotelRoomNumber);
            hotelRoomBookingHistories.add(hotelRoomBookingHistory);
            return hotelRoomBookingHistory;
        } else {
            return history.get();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HotelBookingHistory that = (HotelBookingHistory) o;

        return new EqualsBuilder().append(hotelId, that.hotelId).isEquals();
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(hotelId).toHashCode();
    }
}
