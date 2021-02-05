package info.pionas.rental.infrastructure.eventchannel.spring;

import info.pionas.rental.domain.agreeement.AgreementAccepted;
import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.booking.BookingAccepted;
import info.pionas.rental.domain.booking.BookingRejected;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.HotelRoomBooked;
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

    @Override
    public void publish(BookingRejected bookingRejected) {
        publisher.publishEvent(bookingRejected);
    }

    @Override
    public void publish(AgreementAccepted agreementAccepted) {
        publisher.publishEvent(agreementAccepted);
    }

}
