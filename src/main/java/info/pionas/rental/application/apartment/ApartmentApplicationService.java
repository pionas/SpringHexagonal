package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentEventsPublisher;
import info.pionas.rental.domain.apartment.ApartmentFactory;
import info.pionas.rental.domain.apartment.ApartmentRepository;
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
        Apartment apartment = apartmentRepository.findById(apartmentBookingDto.getApartmentId());
        Period period = new Period(apartmentBookingDto.getStart(), apartmentBookingDto.getEnd());

        Booking booking = apartment.book(apartmentBookingDto.getTenantId(), period, apartmentEventsPublisher);

        return bookingRepository.save(booking);
    }
}
