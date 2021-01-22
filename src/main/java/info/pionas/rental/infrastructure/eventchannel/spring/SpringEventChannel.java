package info.pionas.rental.infrastructure.eventchannel.spring;

import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.apartment.BookingAccepted;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotelroom.HotelRoomBooked;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SpringEventChannel implements EventChannel {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(ApartmentBooked apartmentBooked) {
        publisher.publishEvent(apartmentBooked);
    }

    @Override
    public void publish(HotelRoomBooked hotelRoomBooked) {
        publisher.publishEvent(hotelRoomBooked);
    }

    @Override
    public void publish(BookingAccepted bookingAccepted) {
        publisher.publishEvent(bookingAccepted);
    }

}
