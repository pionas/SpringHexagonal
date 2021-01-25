package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.booking.Booking;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.booking.BookingAssertion.assertThat;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaBookingRepositoryIntegrationTest {
    @Autowired
    private JpaBookingRepository repository;
    @Autowired
    private SpringJpaBookingRepository jpaRepository;
    private String bookingId;

    @AfterEach
    void deleteBooking() {
        if (bookingId != null) {
            jpaRepository.deleteById(UUID.fromString(bookingId));
        }
    }

    @Test
    @Transactional
    void shouldFindExistingBooking() {
        List<LocalDate> days = asList(LocalDate.of(2020, 6, 1), LocalDate.of(2020, 6, 2), LocalDate.of(2020, 6, 4));
        String rentalPlaceId = randomId();
        String tenantId = randomId();
        Booking booking = Booking.hotelRoom(rentalPlaceId, tenantId, days);
        bookingId = repository.save(booking);

        Booking actual = repository.findById(bookingId);

        assertThat(actual)
                .isOpen()
                .isHotelRoom()
                .hasRentalPlaceIdEqualTo(rentalPlaceId)
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(days);
    }

    @Test
    void shouldThrowExceptionWhenNoBookingFound() {
        String id = UUID.randomUUID().toString();

        BookingDoesNotExistException actual = assertThrows(BookingDoesNotExistException.class, () -> repository.findById(id));

        Assertions.assertThat(actual).hasMessage("Booking with id " + id + " does not exist");
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }
}