package info.pionas.rental.application.commandregisty;

import info.pionas.rental.application.booking.BookingAccept;
import info.pionas.rental.application.booking.BookingReject;

public interface CommandRegisty {

    void register(BookingReject bookingReject);

    void register(BookingAccept bookingAccept);
}
