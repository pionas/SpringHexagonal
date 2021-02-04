package info.pionas.rental.application.hotelbookinghistory;

import info.pionas.rental.domain.hotel.HotelRoomBooked;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelBookingHistoryEventListener {

    private final HotelBookingHistoryRepository hotelBookingHistoryRepository;

    @EventListener
    public void consume(HotelRoomBooked hotelRoomBooked) {
        HotelBookingHistory hotelBookingHistory = getHotelBookingHistory(hotelRoomBooked.getHotelId());

        hotelBookingHistory.add(
                hotelRoomBooked.getHotelRoomNumber(), hotelRoomBooked.getEventCreationDateTime(), hotelRoomBooked.getTenantId(),
                hotelRoomBooked.getDays());

        hotelBookingHistoryRepository.save(hotelBookingHistory);
    }

    private HotelBookingHistory getHotelBookingHistory(String hotelId) {
        if (hotelBookingHistoryRepository.existsFor(hotelId)) {
            return hotelBookingHistoryRepository.findFor(hotelId);
        } else {
            return new HotelBookingHistory(hotelId);
        }
    }
}
