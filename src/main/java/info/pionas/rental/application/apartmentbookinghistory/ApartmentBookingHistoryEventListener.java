package info.pionas.rental.application.apartmentbookinghistory;

import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBooking;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import info.pionas.rental.domain.apartmentbookinghistory.BookingPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentBookingHistoryEventListener {

    private final ApartmentBookingHistoryRepository apartmentBookingHistoryRepository;

    @EventListener
    public void consume(ApartmentBooked apartmentBooked) {
        ApartmentBookingHistory apartmentBookingHistory = getApartmentBookingHistoryFor(apartmentBooked.getApartmentId());
        BookingPeriod bookingPeriod = new BookingPeriod(apartmentBooked.getPeriodStart(), apartmentBooked.getPeriodEnd());
        apartmentBookingHistory.add(
                ApartmentBooking.start(
                        apartmentBooked.getEventCreationDateTime(),
                        apartmentBooked.getOwnerId(),
                        apartmentBooked.getTenantId(),
                        bookingPeriod
                )
        );
        apartmentBookingHistoryRepository.save(apartmentBookingHistory);
    }

    private ApartmentBookingHistory getApartmentBookingHistoryFor(String apartmentId) {
        if (apartmentBookingHistoryRepository.existsFor(apartmentId)) {
            return apartmentBookingHistoryRepository.findFor(apartmentId);
        }
        return new ApartmentBookingHistory(apartmentId);
    }
}
