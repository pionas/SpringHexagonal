package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "APARTMENT")
@SuppressWarnings("PMD.UnusedPrivateField")
public class Apartment {

    @Id
    @GeneratedValue
    private UUID id;

    private String ownerId;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "APARTMENT_ROOM", joinColumns = @JoinColumn(name = "APARTMENT_ID"))
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
        if (id == null) {
            return null;
        }
        return id.toString();
    }
}
