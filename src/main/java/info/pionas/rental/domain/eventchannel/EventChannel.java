package info.pionas.rental.domain.eventchannel;

import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.apartment.BookingAccepted;
import info.pionas.rental.domain.hotelroom.HotelRoomBooked;

/**
 * @author Adi
 */
public interface EventChannel {

    void publish(ApartmentBooked apartmentBooked);

    void publish(HotelRoomBooked hotelRoomBooked);

    void publish(BookingAccepted bookingAccepted);

}
