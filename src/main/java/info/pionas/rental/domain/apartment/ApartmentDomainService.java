package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.tenant.TenantNotFoundException;
import info.pionas.rental.domain.tenant.TenantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApartmentDomainService {
    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;
    private final BookingRepository bookingRepository;
    private final ApartmentEventsPublisher apartmentEventsPublisher;

    public Booking book(NewApartmentBookingDto newApartmentBookingDto) {
        if (!apartmentRepository.existById(newApartmentBookingDto.getApartmentId())) {
            throw new ApartmentNotFoundException(newApartmentBookingDto.getApartmentId());
        }
        if (!tenantRepository.existById(newApartmentBookingDto.getTenantId())) {
            throw new TenantNotFoundException(newApartmentBookingDto.getTenantId());
        }
        Apartment apartment = apartmentRepository.findById(newApartmentBookingDto.getApartmentId());
        List<Booking> bookings = bookingRepository.findAllAcceptedBy(apartment.rentalPlaceIdentifier());
        Period period = Period.from(newApartmentBookingDto.getStart(), newApartmentBookingDto.getEnd());

        return apartment.book(bookings, newApartmentBookingDto.getTenantId(), period, apartmentEventsPublisher);
    }
}
