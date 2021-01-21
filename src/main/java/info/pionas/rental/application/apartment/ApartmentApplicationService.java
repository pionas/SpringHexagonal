package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Adi
 */
@Service
@RequiredArgsConstructor
public class ApartmentApplicationService {

    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final EventChannel eventChannel;

    public void add(
            String ownerId, String street, String postalCode, String houseNumber, String apartmentNumber,
            String city, String country, String description, Map<String, Double> roomsDefinition) {

        Apartment apartment = new ApartmentFactory().create(
                ownerId,
                street,
                postalCode,
                houseNumber,
                apartmentNumber,
                city,
                country,
                description,
                roomsDefinition
        );

        apartmentRepository.save(apartment);
    }

    public void book(String id, String tenentId, LocalDate start, LocalDate end) {
        Apartment apartment = apartmentRepository.findById(id);
        Period period = new Period(start, end);
        Booking booking = apartment.book(tenentId, period, eventChannel);

        bookingRepository.save(booking);
    }
}
