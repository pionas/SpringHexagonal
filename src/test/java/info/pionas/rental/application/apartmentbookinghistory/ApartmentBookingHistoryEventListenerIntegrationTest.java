package info.pionas.rental.application.apartmentbookinghistory;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.apartment.ApartmentApplicationService;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentFactory;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingAssertion;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryAssertion;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartment.SpringJpaApartmentTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory.SpringJpaApartmentBookingHistoryTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;

@SpringBootTest
class ApartmentBookingHistoryEventListenerIntegrationTest {
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    @Autowired
    private ApartmentApplicationService apartmentApplicationService;
    @Autowired
    private ApartmentBookingHistoryRepository apartmentBookingHistoryRepository;
    @Autowired
    private SpringJpaApartmentBookingHistoryTestRepository springJpaApartmentBookingHistoryTestRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private SpringJpaApartmentTestRepository springJpaApartmentTestRepository;

    private String apartmentId;

    @AfterEach
    void removeApartment() {
        springJpaApartmentTestRepository.deleteById(apartmentId);
        springJpaApartmentBookingHistoryTestRepository.deleteById(apartmentId);
    }

    @Test
    @Transactional
    void shouldUpdateApartmentBookingHistory() {
        String tenantId = "11223344";
        LocalDate start = LocalDate.of(2020, 1, 13);
        LocalDate end = LocalDate.of(2020, 1, 14);
        givenExistingApartment();

        apartmentApplicationService.book(apartmentId, tenantId, start, end);
        ApartmentBookingHistory actual = apartmentBookingHistoryRepository.findFor(apartmentId);

        ApartmentBookingHistoryAssertion.assertThat(actual)
                .hasOneApartmentBooking()
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .hasOwnerIdEqualTo(OWNER_ID)
                            .hasTenantIdEqualTo(tenantId)
                            .hasBookingPeriodThatHas(start, end);
                });
    }

    private void givenExistingApartment() {
        apartmentId = apartmentRepository.save(createApartment());
    }

    private Apartment createApartment() {
        return new ApartmentFactory().create(
                OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY,
                DESCRIPTION, ROOMS_DEFINITION);
    }
}