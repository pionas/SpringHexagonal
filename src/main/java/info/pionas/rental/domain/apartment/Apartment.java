package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "APARTMENT")
public class Apartment {

    @Id
    @GeneratedValue
    private UUID id;

    private String ownerId;

    @Embedded
    private Address address;

    @ElementCollection
    private List<Room> rooms;

    private String description;

    public Apartment(String ownerId, Address address, List<Room> rooms, String description) {
        this.ownerId = ownerId;
        this.address = address;
        this.rooms = rooms;
        this.description = description;
    }

    public Booking book(String tenantId, Period period, EventChannel eventChannel) {
        ApartmentBooked apartmentBooked = ApartmentBooked.create(id(), ownerId, tenantId, period);
        eventChannel.publish(apartmentBooked);
        return Booking.apartment(id(), tenantId, period);
    }

    public String id() {
        return id.toString();
    }
}
