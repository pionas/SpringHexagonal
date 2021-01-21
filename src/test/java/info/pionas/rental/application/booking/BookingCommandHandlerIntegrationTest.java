package info.pionas.rental.application.booking;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingAssertion;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.infrastructure.rest.api.booking.BookingRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static java.util.Arrays.asList;

@SpringBootTest
class BookingCommandHandlerIntegrationTest {

    @Autowired
    private BookingRestController bookingRestController;
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void shouldAcceptBooking() {
        String bookingId = givenBooking();

        bookingRestController.accept(bookingId);

        Booking actual = bookingRepository.findById(bookingId);
        BookingAssertion
                .assertThat(actual)
                .isAccept();
    }

    @Test
    void shouldRejectBooking() {
        String bookingId = givenBooking();

        bookingRestController.reject(bookingId);

        Booking actual = bookingRepository.findById(bookingId);
        BookingAssertion
                .assertThat(actual)
                .isReject();
    }

    private String givenBooking() {
        Booking booking = Booking.hotelRoom("1234", "567", asList(LocalDate.now(), LocalDate.now().plusDays(1)));
        return bookingRepository.save(booking);
    }

}