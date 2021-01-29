package info.pionas.rental.domain.apartmentbookinghistory;

import info.pionas.rental.domain.period.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

class ApartmentBookingHistoryTest {
    private static final String APARTMENT_ID = "1234";
    private static final String OWNER_ID = "5678";
    private static final LocalDateTime BOOKING_DATE_TIME_1 = LocalDateTime.of(2020, 2, 4, 6, 8);
    private static final String TENANT_ID_1 = "8989";
    private static final LocalDate START_1 = LocalDate.of(2020, 10, 11);
    private static final LocalDate END_1 = LocalDate.of(2020, 10, 12);
    private static final LocalDateTime BOOKING_DATE_TIME_2 = LocalDateTime.of(2020, 1, 3, 5, 7);
    private static final String TENANT_ID_2 = "1212";
    private static final LocalDate START_2 = LocalDate.of(2020, 1, 2);
    private static final LocalDate END_2 = LocalDate.of(2020, 3, 4);

    @Test
    void shouldAddFirstApartmentBookingIntoHistory() {
        ApartmentBookingHistory actual = new ApartmentBookingHistory(APARTMENT_ID);
        actual.addBookingStart(BOOKING_DATE_TIME_1, OWNER_ID, TENANT_ID_1, new Period(START_1, END_1));

        ApartmentBookingHistoryAssertion.assertThat(actual)
                .hasApartmentIdEqualsTo(APARTMENT_ID)
                .hasOneApartmentBooking()
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .isStart()
                            .hasBookingDateTimeEqualTo(BOOKING_DATE_TIME_1)
                            .hasOwnerIdEqualTo(OWNER_ID)
                            .hasTenantIdEqualTo(TENANT_ID_1)
                            .hasPeriodThatHas(START_1, END_1);
                });
    }

    @Test
    void shouldAddNextApartmentBookingIntoHistory() {
        ApartmentBookingHistory actual = new ApartmentBookingHistory(APARTMENT_ID);

        actual.addBookingStart(BOOKING_DATE_TIME_1, OWNER_ID, TENANT_ID_1, new Period(START_1, END_1));
        actual.addBookingStart(BOOKING_DATE_TIME_2, OWNER_ID, TENANT_ID_2, new Period(START_2, END_2));

        ApartmentBookingHistoryAssertion.assertThat(actual)
                .hasApartmentIdEqualsTo(APARTMENT_ID)
                .hasApartmentBookingsAmount(2)
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .isStart()
                            .hasBookingDateTimeEqualTo(BOOKING_DATE_TIME_1)
                            .hasOwnerIdEqualTo(OWNER_ID)
                            .hasTenantIdEqualTo(TENANT_ID_1)
                            .hasPeriodThatHas(START_1, END_1);
                })
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .isStart()
                            .hasBookingDateTimeEqualTo(BOOKING_DATE_TIME_2)
                            .hasOwnerIdEqualTo(OWNER_ID)
                            .hasTenantIdEqualTo(TENANT_ID_2)
                            .hasPeriodThatHas(START_2, END_2);
                });
    }
}