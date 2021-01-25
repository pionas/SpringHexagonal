package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;

import java.time.LocalDate;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;

public class ApartmentApplicationService {

    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final ApartmentEventsPublisher publisher;

    ApartmentApplicationService(ApartmentRepository apartmentRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        this.apartmentRepository = apartmentRepository;
        this.bookingRepository = bookingRepository;
        this.publisher = new ApartmentEventsPublisher(new EventIdFactory(), eventChannel);
    }

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
        Booking booking = apartment.book(tenantId, period, publisher);
        return bookingRepository.save(booking);
    }
}
