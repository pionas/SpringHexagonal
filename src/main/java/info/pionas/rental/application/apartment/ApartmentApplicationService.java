package info.pionas.rental.application.apartment;


import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentDomainService;
import info.pionas.rental.domain.apartment.ApartmentFactory;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ApartmentApplicationService {

    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final ApartmentFactory apartmentFactory;
    private final ApartmentDomainService apartmentDomainService;

    public String add(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentFactory.create(apartmentDto.asNewApartmentDto());

        return apartmentRepository.save(apartment);
    }

    public UUID book(ApartmentBookingDto apartmentBookingDto) {
        Booking booking = apartmentDomainService.book(apartmentBookingDto.asNewApartmentBookingDto());

        return bookingRepository.save(booking);
    }
}
