package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.period.Period;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartmentDomainService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentEventsPublisher apartmentEventsPublisher;

    public Booking book(NewApartmentBookingDto newApartmentBookingDto) {
        if (!apartmentRepository.existById(newApartmentBookingDto.getApartmentId())) {
            throw new ApartmentNotFoundException(newApartmentBookingDto.getApartmentId());
        }
        Apartment apartment = apartmentRepository.findById(newApartmentBookingDto.getApartmentId());
        Period period = new Period(newApartmentBookingDto.getStart(), newApartmentBookingDto.getEnd());

        return apartment.book(newApartmentBookingDto.getTenantId(), period, apartmentEventsPublisher);
    }
}
