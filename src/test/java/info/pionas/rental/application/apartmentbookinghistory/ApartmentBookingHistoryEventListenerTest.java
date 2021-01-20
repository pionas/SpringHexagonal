package info.pionas.rental.application.apartmentbookinghistory;

import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.apartment.ApartmentBookedTestFactory;
import info.pionas.rental.domain.apartment.Period;
import info.pionas.rental.domain.apartmentbookinghistory.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

/**
 * @author Adi
 */
public class ApartmentBookingHistoryEventListenerTest {

    private static final String APARTMENT_ID = "123";
    private static final String OWNER_ID = "456";
    private static final String TENANT_ID = "789";
    private static final LocalDate START = LocalDate.of(2020, 10, 11);
    private static final LocalDate END = LocalDate.of(2020, 10, 12);
    private static final Period PERIOD = new Period(START, END);
    public static final int FIRST_BOOKING = 1;
    public static final int NEXT_BOOKING = 2;

    private final ArgumentCaptor<ApartmentBookingHistory> captor = ArgumentCaptor.forClass(ApartmentBookingHistory.class);
    private final ApartmentBookingHistoryRepository repository = mock(ApartmentBookingHistoryRepository.class);
    private final ApartmentBookingHistoryEventListener eventListener = new ApartmentBookingHistoryEventListener(repository);

    @Test
    public void shouldCreateApartmentBookingHistoryWhenConsumingApartmentBooked() {
        givenNotExistingApartmentBookingHistory();
        eventListener.consume(givenApartmentBooked());

        then(repository).should().save(captor.capture());
        thenApartmentBookingBookingShouldHaveApartmentBookings(captor.getValue(), FIRST_BOOKING);
    }

    @Test
    public void shouldUpdateExistingApartmentBookingHistoryWhenConsumingApartmentBooked() {
        givenExistingApartmentBookingHistory();
        eventListener.consume(givenApartmentBooked());

        then(repository).should().save(captor.capture());
        thenApartmentBookingBookingShouldHaveApartmentBookings(captor.getValue(), NEXT_BOOKING);
    }

    private ApartmentBooked givenApartmentBooked() {
        return ApartmentBookedTestFactory.create(APARTMENT_ID, OWNER_ID, TENANT_ID, PERIOD);
    }

    private void givenNotExistingApartmentBookingHistory() {
        given(repository.existFor(APARTMENT_ID)).willReturn(false);
    }

    private void givenExistingApartmentBookingHistory() {
        given(repository.existFor(APARTMENT_ID)).willReturn(true);
        ApartmentBookingHistory apartmentBookingHistory = getApartmentBookingHistory();
        given(repository.findFor(APARTMENT_ID)).willReturn(apartmentBookingHistory);
    }

    private ApartmentBookingHistory getApartmentBookingHistory() {
        ApartmentBookingHistory apartmentBookingHistory = new ApartmentBookingHistory(APARTMENT_ID);
        apartmentBookingHistory.add(
                ApartmentBooking.start(
                        LocalDateTime.now(),
                        OWNER_ID,
                        "9087",
                        new BookingPeriod(LocalDate.now(), LocalDate.now().plusDays(1))
                )
        );
        return apartmentBookingHistory;
    }

    private void thenApartmentBookingBookingShouldHaveApartmentBookings(ApartmentBookingHistory actual, int bookingsSize) {
        Assertions.assertThat(actual).extracting("bookings").satisfies(actualBookings -> {
            List<ApartmentBooking> bookings = (List<ApartmentBooking>) actualBookings;
            Assertions.assertThat(bookings)
                    .hasSize(bookingsSize)
                    .anySatisfy(actualBooking -> {
                                ApartmentBookingAssertion.assertThat(actualBooking)
                                        .hasOwnerIdEqualTo(OWNER_ID)
                                        .hasTenantIdEqualTo(TENANT_ID)
                                        .hasBookingPeriodThatHas(START, END);
                            }
                    );
        });
    }

}
