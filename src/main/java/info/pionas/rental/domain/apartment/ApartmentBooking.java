package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ApartmentBooking {
    private final List<Booking> bookings;
    private final String tenantId;
    private final Period period;
    private final Money price;
    private final ApartmentEventsPublisher apartmentEventsPublisher;

}
