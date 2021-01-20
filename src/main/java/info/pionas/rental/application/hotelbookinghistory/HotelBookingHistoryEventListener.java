package info.pionas.rental.application.hotelbookinghistory;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelroom.HotelRoomBooked;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
public class HotelBookingHistoryEventListener {

    private final HotelBookingHistoryRepository hotelRoomBookingHistoryRepository;

    @EventListener
    public void consume(HotelRoomBooked hotelRoomBooked) {
        HotelBookingHistory hotelBookingHistory = getHotelRoomBookingHistoryFor(hotelRoomBooked.getHotelId());
        hotelBookingHistory.add(
                hotelRoomBooked.getHotelRoomId(),
                hotelRoomBooked.getEventCreationDateTime(),
                hotelRoomBooked.getTenantId(),
                hotelRoomBooked.getDays()
        );
        hotelRoomBookingHistoryRepository.save(hotelBookingHistory);
    }

    private HotelBookingHistory getHotelRoomBookingHistoryFor(String hotelId) {
        if (hotelRoomBookingHistoryRepository.existFor(hotelId)) {
            return hotelRoomBookingHistoryRepository.findFor(hotelId);
        }
        return new HotelBookingHistory(hotelId);
    }
}
