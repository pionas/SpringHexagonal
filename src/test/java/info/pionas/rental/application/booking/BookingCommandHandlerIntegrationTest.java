package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.infrastructure.persistence.jpa.booking.SpringJpaBookingTestRepository;
import info.pionas.rental.infrastructure.rest.api.booking.BookingRestController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.Arrays.asList;

@SpringBootTest
@Tag("IntegrationTest")
class BookingCommandHandlerIntegrationTest {
    @Autowired
    private BookingRestController controller;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SpringJpaBookingTestRepository springJpaBookingTestRepository;

    private UUID bookingId;

    @AfterEach
    void removeBookings() {
        springJpaBookingTestRepository.deleteById(bookingId);
    }

    @Test
    void shouldAcceptBooking() {
        givenOpenBooking();

        controller.accept(bookingId.toString());
        Booking actual = bookingRepository.findById(bookingId.toString());

        BookingAssertion.assertThat(actual).isAccepted();
    }

    @Test
    void shouldRejectBooking() {
        givenOpenBooking();

        controller.reject(bookingId.toString());
        Booking actual = bookingRepository.findById(bookingId.toString());

        BookingAssertion.assertThat(actual).isRejected();
    }

    private void givenOpenBooking() {
        Booking booking = Booking.hotelRoom("1234", "5678", asList(LocalDate.now(), LocalDate.now().plusDays(1)));
        bookingId = bookingRepository.save(booking);
    }
}