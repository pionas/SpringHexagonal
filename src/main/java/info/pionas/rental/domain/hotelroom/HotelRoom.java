package info.pionas.rental.domain.hotelroom;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "HOTEL_ROOM")
public class HotelRoom {

    @Id
    @GeneratedValue
    private UUID id;
    private String hotelId;
    private int number;
    @ElementCollection
    private List<Space> spaces;
    private String description;

    public HotelRoom(String hotelId, int number, List<Space> spaces, String description) {
        this.hotelId = hotelId;
        this.number = number;
        this.spaces = spaces;
        this.description = description;
    }

    public Booking book(String tenantId, List<LocalDate> days, EventChannel eventChannel) {
        HotelRoomBooked hotelRoomBooked = HotelRoomBooked.create(id(), hotelId, tenantId, days);
        eventChannel.publish(hotelRoomBooked);
        return Booking.hotelRoom(id(), tenantId, days);
    }

    public String id() {
        return id.toString();
    }
}
