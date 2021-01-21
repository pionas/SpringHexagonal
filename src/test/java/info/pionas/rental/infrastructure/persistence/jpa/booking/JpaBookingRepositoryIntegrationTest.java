package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingAssertion;
import info.pionas.rental.domain.apartment.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JpaBookingRepositoryIntegrationTest {
    public static final String RENTAL_PLACE_ID = "123";
    public static final String TENANT_ID = "456";
    public static final List<LocalDate> DAYS = asList(
            LocalDate.of(2020, 5, 5),
            LocalDate.of(2020, 5, 6),
            LocalDate.of(2020, 5, 7)
    );

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SpringJpaBookingTestRepository springJpaBookingTestRepository;

    private final List<String> bookingIds = new ArrayList<>();

    @AfterEach
    void deleteBookings() {
        springJpaBookingTestRepository.deleteAll(bookingIds);
    }

    @Test
    void shouldThrowExceptionWhenBookingDoesNotExist() {
        String nonExistingBookingId = UUID.randomUUID().toString();

        BookingDoesNotExistException actual = assertThrows(BookingDoesNotExistException.class, () -> {
            bookingRepository.findById(nonExistingBookingId);
        });

        assertThat(actual).hasMessage("Booking with id " + nonExistingBookingId + " does not exist");
    }

    @Test
    @Transactional
    void shouldReturnExistingBooking() {
        String existingId = givenExistingBooking(createBooking());

        Booking actual = bookingRepository.findById(existingId);

        BookingAssertion.assertThat(actual)
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    @Test
    @Transactional
    void shouldReturnExistingBookingWeWant() {
        Booking booking1 = Booking.hotelRoom("1234", "12", DAYS);
        givenExistingBooking(booking1);
        String existingId = givenExistingBooking(createBooking());
        Booking booking2 = Booking.hotelRoom("5692", "10", DAYS);
        givenExistingBooking(booking2);
        Booking booking3 = Booking.hotelRoom("2083", "11", DAYS);
        givenExistingBooking(booking3);

        Booking actual = bookingRepository.findById(existingId);

        BookingAssertion.assertThat(actual)
                .isHotelRoom()
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    private String givenExistingBooking(Booking booking) {
        String bookingId = bookingRepository.save(booking);
        bookingIds.add(bookingId);

        return bookingId;
    }

    private Booking createBooking() {
        return Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
    }
}