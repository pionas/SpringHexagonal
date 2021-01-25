package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentEventsPublisher;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartment.Period;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;

@RequiredArgsConstructor
public class ApartmentApplicationService {

    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final ApartmentEventsPublisher apartmentEventsPublisher;

    public String add(ApartmentDto apartmentDto) {
        Apartment apartment = apartment()
                .withOwnerId(apartmentDto.getOwnerId())
                .withStreet(apartmentDto.getStreet())
                .withPostalCode(apartmentDto.getPostalCode())
                .withHouseNumber(apartmentDto.getHouseNumber())
                .withApartmentNumber(apartmentDto.getApartmentNumber())
                .withCity(apartmentDto.getCity())
                .withCountry(apartmentDto.getCountry())
                .withDescription(apartmentDto.getDescription())
                .withRoomsDefinition(apartmentDto.getRoomsDefinition())
                .build();

        return apartmentRepository.save(apartment);
    }

    public String book(String apartmentId, String tenantId, LocalDate start, LocalDate end) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        Period period = new Period(start, end);
        Booking booking = apartment.book(tenantId, period, apartmentEventsPublisher);
        return bookingRepository.save(booking);
    }
}
