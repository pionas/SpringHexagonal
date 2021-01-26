package info.pionas.rental.application.apartmentbookinghistory;

import info.pionas.rental.domain.apartment.ApartmentBooked;
import info.pionas.rental.domain.apartment.Period;
import info.pionas.rental.domain.apartmentbookinghistory.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static info.pionas.rental.domain.apartment.ApartmentBooked.Builder.apartmentBooked;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ApartmentBookingHistoryEventListenerTest {
    private static final String APARTMENT_ID = "1234";
    private static final String OWNER_ID = "5678";
    private static final String TENANT_ID = "8989";
    private static final LocalDate START = LocalDate.of(2020, 10, 11);
    private static final LocalDate END = LocalDate.of(2020, 10, 12);
    private static final Period PERIOD = new Period(START, END);
    private static final int FIRST_BOOKING = 1;
    private static final int NEXT_BOOKING = 2;

    private final ArgumentCaptor<ApartmentBookingHistory> captor = ArgumentCaptor.forClass(ApartmentBookingHistory.class);
    private final ApartmentBookingHistoryRepository repository = mock(ApartmentBookingHistoryRepository.class);
    private final ApartmentBookingHistoryEventListener eventListener = new ApartmentBookingHistoryEventListener(repository);

    @Test
    void shouldCreateApartmentBookingHistoryWhenConsumingApartmentBooked() {
        givenNotExistingApartmentBookingHistory();

        eventListener.consume(givenApartmentBooked());

        then(repository).should().save(captor.capture());
        thenApartmentBookingHistoryShouldHaveApartmentBookings(captor.getValue(), FIRST_BOOKING);
    }

    private void givenNotExistingApartmentBookingHistory() {
        BDDMockito.given(repository.existsFor(APARTMENT_ID)).willReturn(false);
    }

    @Test
    void shouldUpdateExistingApartmentBookingHistoryWhenConsumingApartmentBooked() {
        givenExistingApartmentBookingHistory();

        eventListener.consume(givenApartmentBooked());

        then(repository).should().save(captor.capture());
        thenApartmentBookingHistoryShouldHaveApartmentBookings(captor.getValue(), NEXT_BOOKING);
    }

    private void thenApartmentBookingHistoryShouldHaveApartmentBookings(ApartmentBookingHistory actual, int bookingsSize) {
        ApartmentBookingHistoryAssertion.assertThat(actual)
                .hasApartmentBookingsAmount(bookingsSize)
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .hasOwnerIdEqualTo(OWNER_ID)
                            .hasTenantIdEqualTo(TENANT_ID)
                            .hasBookingPeriodThatHas(START, END);
                });
    }

    private void givenExistingApartmentBookingHistory() {
        given(repository.existsFor(APARTMENT_ID)).willReturn(true);
        ApartmentBookingHistory apartmentBookingHistory = getApartmentBookingHistory();
        given(repository.findFor(APARTMENT_ID)).willReturn(apartmentBookingHistory);
    }

    private ApartmentBookingHistory getApartmentBookingHistory() {
        ApartmentBookingHistory apartmentBookingHistory = new ApartmentBookingHistory(APARTMENT_ID);
        ApartmentBooking apartmentBooking = ApartmentBooking.start(
                LocalDateTime.now(), OWNER_ID, "9807", new BookingPeriod(LocalDate.now(), LocalDate.now().plusDays(1)));
        apartmentBookingHistory.add(apartmentBooking);
        return apartmentBookingHistory;
    }

    private ApartmentBooked givenApartmentBooked() {
        return apartmentBooked()
                .withEventId("232132")
                .withApartmentId(APARTMENT_ID)
                .withOwnerId(OWNER_ID)
                .withTenantId(TENANT_ID)
                .withPeriod(PERIOD)
                .build();
    }
}