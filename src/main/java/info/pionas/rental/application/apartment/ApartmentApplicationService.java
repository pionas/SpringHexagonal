package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.period.Period;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartmentApplicationService {

    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final ApartmentEventsPublisher apartmentEventsPublisher;
    private final ApartmentFactory apartmentFactory;

    public String add(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentFactory.create(apartmentDto.asNewApartmentDto());

        return apartmentRepository.save(apartment);
    }

    public String book(ApartmentBookingDto apartmentBookingDto) {
        Booking booking = new ApartmentDomainService(apartmentRepository, apartmentEventsPublisher).book(apartmentBookingDto.asNewApartmentBookingDto());

        return bookingRepository.save(booking);
    }
}
