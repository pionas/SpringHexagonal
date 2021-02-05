package info.pionas.rental.domain.eventchannel;

import info.pionas.rental.domain.agreeement.AgreementAccepted;
import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.booking.BookingAccepted;
import info.pionas.rental.domain.booking.BookingRejected;
import info.pionas.rental.domain.hotel.HotelRoomBooked;


public interface EventChannel {

    void publish(ApartmentBooked apartmentBooked);

    void publish(HotelRoomBooked hotelRoomBooked);

    void publish(BookingAccepted bookingAccepted);

    void publish(BookingRejected bookingRejected);

    void publish(AgreementAccepted agreementAccepted);
}
