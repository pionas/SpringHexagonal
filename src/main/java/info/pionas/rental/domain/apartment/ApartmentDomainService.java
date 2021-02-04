package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferNotFoundException;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
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
    private final ApartmentOfferRepository apartmentOfferRepository;
    private final ApartmentEventsPublisher apartmentEventsPublisher;

    public Booking book(NewApartmentBookingDto newApartmentBookingDto) {
        verifyExistenceOfAggregates(newApartmentBookingDto);
        return bookApartment(newApartmentBookingDto);
    }

    private Booking bookApartment(NewApartmentBookingDto newApartmentBookingDto) {
        Apartment apartment = apartmentRepository.findById(newApartmentBookingDto.getApartmentId());
        List<Booking> bookings = bookingRepository.findAllAcceptedBy(apartment.rentalPlaceIdentifier());
        ApartmentOffer apartmentOffer = apartmentOfferRepository.findByApartmentId(newApartmentBookingDto.getApartmentId());
        Period period = Period.from(newApartmentBookingDto.getStart(), newApartmentBookingDto.getEnd());

        if (apartmentOffer.hasAvailabilityWithin(period)) {
            ApartmentBooking apartmentBooking = new ApartmentBooking(
                    bookings, newApartmentBookingDto.getTenantId(), period, apartmentOffer.getMoney(), apartmentEventsPublisher);

            return apartment.book(apartmentBooking);
        } else {
            throw new AparmentBookingException(newApartmentBookingDto.getStart(), newApartmentBookingDto.getEnd());
        }
    }

    private void verifyExistenceOfAggregates(NewApartmentBookingDto newApartmentBookingDto) {
        if (!apartmentRepository.existById(newApartmentBookingDto.getApartmentId())) {
            throw new ApartmentNotFoundException(newApartmentBookingDto.getApartmentId());
        }

        if (!tenantRepository.existById(newApartmentBookingDto.getTenantId())) {
            throw new TenantNotFoundException(newApartmentBookingDto.getTenantId());
        }

        if (!apartmentOfferRepository.existByApartmentId(newApartmentBookingDto.getApartmentId())) {
            throw new ApartmentOfferNotFoundException(newApartmentBookingDto.getApartmentId());
        }
    }
}
