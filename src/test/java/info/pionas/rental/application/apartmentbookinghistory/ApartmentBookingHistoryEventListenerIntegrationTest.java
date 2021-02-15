package info.pionas.rental.application.apartmentbookinghistory;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.apartment.ApartmentApplicationService;
import info.pionas.rental.application.apartment.ApartmentBookingDto;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingAssertion;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryAssertion;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.domain.tenant.Tenant;
import info.pionas.rental.domain.tenant.TenantRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartment.SpringJpaApartmentTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory.SpringJpaApartmentBookingHistoryTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer.SpringJpaApartmentOfferTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.tenant.SpringJpaTenantTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;
import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;

@SpringBootTest
@Tag("IntegrationTest")
class ApartmentBookingHistoryEventListenerIntegrationTest {
    private static final String LOGIN = "john.doe";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final BigDecimal PRICE = BigDecimal.valueOf(123.45);
    private static final LocalDate START = LocalDate.of(2030, 1, 1);
    private static final LocalDate END = LocalDate.of(2050, 1, 1);

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
    @Autowired
    private ApartmentOfferRepository apartmentOfferRepository;
    @Autowired
    private SpringJpaApartmentOfferTestRepository springJpaApartmentOfferTestRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private SpringJpaTenantTestRepository springJpaTenantTestRepository;

    private String apartmentId;
    private UUID apartmentOfferId;
    private String tenantId;

    @AfterEach
    void removeApartment() {
        springJpaApartmentTestRepository.deleteById(apartmentId);
        springJpaApartmentBookingHistoryTestRepository.deleteById(apartmentId);
        springJpaApartmentOfferTestRepository.deleteById(apartmentOfferId);
        springJpaTenantTestRepository.deleteById(tenantId);
    }

    @Test
    @Transactional
    void shouldUpdateApartmentBookingHistory() {
        LocalDate start = LocalDate.of(2040, 1, 13);
        LocalDate end = LocalDate.of(2040, 1, 14);
        givenExistingApartmentWithOffer();
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto(apartmentId, tenantId, start, end);

        apartmentApplicationService.book(apartmentBookingDto);
        ApartmentBookingHistory actual = apartmentBookingHistoryRepository.findFor(apartmentId);

        ApartmentBookingHistoryAssertion.assertThat(actual)
                .hasOneApartmentBooking()
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .hasOwnerIdEqualTo(OWNER_ID)
                            .hasTenantIdEqualTo(tenantId)
                            .hasPeriodThatHas(start, end);
                });
    }

    private void givenExistingApartmentWithOffer() {
        apartmentId = apartmentRepository.save(createApartment());
        apartmentOfferId = apartmentOfferRepository.save(createApartmentOffer());
        tenantId = tenantRepository.save(createTenant());
    }

    private Tenant createTenant() {
        return tenant()
                .withLogin(LOGIN)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(EMAIL)
                .build();
    }

    private ApartmentOffer createApartmentOffer() {
        return apartmentOffer()
                .withApartmentId(apartmentId)
                .withPrice(PRICE)
                .withAvailability(START, END)
                .build();
    }

    private Apartment createApartment() {
        return apartment()
                .withOwnerId(OWNER_ID)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withHouseNumber(HOUSE_NUMBER)
                .withApartmentNumber(APARTMENT_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .withDescription(DESCRIPTION)
                .withSpacesDefinition(SPACES_DEFINITION)
                .build();
    }
}