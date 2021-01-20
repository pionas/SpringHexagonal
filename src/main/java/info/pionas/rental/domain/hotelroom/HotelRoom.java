package info.pionas.rental.domain.hotelroom;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.eventchannel.EventChannel;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@AllArgsConstructor
//@Entity
@Table(name = "HOTEL_ROOM")
public class HotelRoom {

    @Id
    @GeneratedValue
    private String hotelRoomId;
    private final String hotelId;
    private final int number;
    @OneToMany
    private final List<Space> spaces;
    private final String description;

    public Booking book(String tenantId, List<LocalDate> days, EventChannel eventChannel) {
        HotelRoomBooked hotelRoomBooked = HotelRoomBooked.create(hotelRoomId, hotelId, tenantId, days);
        eventChannel.publish(hotelRoomBooked);
        return Booking.hotelRoom(hotelRoomId, tenantId, days);
    }
}
