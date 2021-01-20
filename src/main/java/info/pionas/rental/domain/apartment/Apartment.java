package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.eventchannel.EventChannel;
import java.util.List;
import javax.persistence.Embedded;
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
@Table(name = "APARTMENT")
public class Apartment {

    @Id
    @GeneratedValue
    private String id;

    private final String ownerId;

    @Embedded
    private final Address address;

    @OneToMany
    private final List<Room> rooms;

    private final String description;

    public Booking book(String tenantId, Period period, EventChannel eventChannel) {
        ApartmentBooked apartmentBooked = ApartmentBooked.create(id, ownerId, tenantId, period);
        eventChannel.publish(apartmentBooked);
        return Booking.apartment(id, tenantId, period);
    }
}
