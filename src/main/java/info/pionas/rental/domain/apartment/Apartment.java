package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "APARTMENT")
public class Apartment {

    @Id
    @GeneratedValue
    private String id;

    private final String ownerId;

    @Embedded
    private final Address address;

    @ElementCollection
    private final List<Room> rooms;

    private final String description;

    public Booking book(String tenantId, Period period, EventChannel eventChannel) {
        ApartmentBooked apartmentBooked = ApartmentBooked.create(id, ownerId, tenantId, period);
        eventChannel.publish(apartmentBooked);
        return Booking.apartment(id, tenantId, period);
    }
}
