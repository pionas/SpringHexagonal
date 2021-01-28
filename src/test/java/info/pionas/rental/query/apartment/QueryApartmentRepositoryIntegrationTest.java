package info.pionas.rental.query.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBooking;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import info.pionas.rental.domain.apartmentbookinghistory.BookingPeriod;
import info.pionas.rental.infrastructure.persistence.jpa.apartment.SpringJpaApartmentTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory.SpringJpaApartmentBookingHistoryTestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("IntegrationTest")
class QueryApartmentRepositoryIntegrationTest {
    private static final String OWNER_ID_1 = "1234";
    private static final String STREET_1 = "Florianska";
    private static final String POSTAL_CODE_1 = "12-345";
    private static final String HOUSE_NUMBER_1 = "1";
    private static final String APARTMENT_NUMBER_1 = "13";
    private static final String CITY_1 = "Cracow";
    private static final String COUNTRY_1 = "Poland";
    private static final String DESCRIPTION_1 = "Nice place to stay";
    private static final Map<String, Double> SPACES_DEFINITION_1 = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final LocalDateTime BOOKING_DATE_TIME_1 = LocalDateTime.of(2020, 12, 10, 11, 12);
    private static final String TENANT_ID_1 = "2468";
    private static final LocalDate BOOKING_START_1 = LocalDate.of(2020, 12, 11);
    private static final LocalDate BOOKING_END_1 = LocalDate.of(2020, 12, 14);
    private static final String OWNER_ID_2 = "4321";
    private static final String STREET_2 = "Grodzka";
    private static final String POSTAL_CODE_2 = "54-321";
    private static final String HOUSE_NUMBER_2 = "13";
    private static final String APARTMENT_NUMBER_2 = "42";
    private static final String CITY_2 = "Berlin";
    private static final String COUNTRY_2 = "Germany";
    private static final String DESCRIPTION_2 = "Lovely place";
    private static final Map<String, Double> SPACES_DEFINITION_2 = ImmutableMap.of("Toilet", 15.0, "RoomOne", 20.0, "RoomTwo", 25.0);

    @Autowired
    private QueryApartmentRepository queryApartmentRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private SpringJpaApartmentTestRepository springJpaApartmentTestRepository;
    @Autowired
    private ApartmentBookingHistoryRepository apartmentBookingHistoryRepository;
    @Autowired
    private SpringJpaApartmentBookingHistoryTestRepository springJpaApartmentBookingHistoryTestRepository;

    private String apartmentId1;
    private String apartmentId2;

    @BeforeEach
    void givenApartments() {
        Apartment apartment1 = apartment()
                .withOwnerId(OWNER_ID_1)
                .withStreet(STREET_1)
                .withPostalCode(POSTAL_CODE_1)
                .withHouseNumber(HOUSE_NUMBER_1)
                .withApartmentNumber(APARTMENT_NUMBER_1)
                .withCity(CITY_1)
                .withCountry(COUNTRY_1)
                .withDescription(DESCRIPTION_1)
                .withSpacesDefinition(SPACES_DEFINITION_1)
                .build();
        apartmentId1 = apartmentRepository.save(apartment1);
        ApartmentBookingHistory apartmentBookingHistory = new ApartmentBookingHistory(apartmentId1);
        apartmentBookingHistory.add(ApartmentBooking.start(BOOKING_DATE_TIME_1, OWNER_ID_1, TENANT_ID_1, new BookingPeriod(BOOKING_START_1, BOOKING_END_1)));
        apartmentBookingHistoryRepository.save(apartmentBookingHistory);

        Apartment apartment2 = apartment()
                .withOwnerId(OWNER_ID_2)
                .withStreet(STREET_2)
                .withPostalCode(POSTAL_CODE_2)
                .withHouseNumber(HOUSE_NUMBER_2)
                .withApartmentNumber(APARTMENT_NUMBER_2)
                .withCity(CITY_2)
                .withCountry(COUNTRY_2)
                .withDescription(DESCRIPTION_2)
                .withSpacesDefinition(SPACES_DEFINITION_2)
                .build();
        apartmentId2 = apartmentRepository.save(apartment2);

        queryApartmentRepository.findAll();
    }

    @AfterEach
    void deleteApartments() {
        springJpaApartmentTestRepository.deleteAll(asList(apartmentId1, apartmentId2));
        springJpaApartmentBookingHistoryTestRepository.deleteById(apartmentId1);
    }

    @Test
    @Transactional
    void shouldFindAllApartments() {
        Iterable<ApartmentReadModel> actual = queryApartmentRepository.findAll();

        assertThat(actual)
                .hasSize(2)
                .anySatisfy(apartmentReadModel -> {
                    ApartmentReadModelAssertion.assertThat(apartmentReadModel)
                            .hasIdEqualsTo(apartmentId1)
                            .hasOwnerIdEqualsTo(OWNER_ID_1)
                            .hasDescriptionEqualsTo(DESCRIPTION_1)
                            .hasAddressEqualsTo(STREET_1, POSTAL_CODE_1, HOUSE_NUMBER_1, APARTMENT_NUMBER_1, CITY_1, COUNTRY_1)
                            .hasRoomsEqualsTo(SPACES_DEFINITION_1);
                })
                .anySatisfy(apartmentReadModel -> {
                    ApartmentReadModelAssertion.assertThat(apartmentReadModel)
                            .hasIdEqualsTo(apartmentId2)
                            .hasOwnerIdEqualsTo(OWNER_ID_2)
                            .hasDescriptionEqualsTo(DESCRIPTION_2)
                            .hasAddressEqualsTo(STREET_2, POSTAL_CODE_2, HOUSE_NUMBER_2, APARTMENT_NUMBER_2, CITY_2, COUNTRY_2)
                            .hasRoomsEqualsTo(SPACES_DEFINITION_2);
                });
    }

    @Test
    void shouldReturnNotExistingApartment() {
        ApartmentDetails actual = queryApartmentRepository.findById(UUID.randomUUID().toString());

        Assertions.assertThat(actual.getApartment()).isNull();
        Assertions.assertThat(actual.getBookingHistory()).isNull();
    }

    @Test
    @Transactional
    void shouldReturnApartmentWithoutHistory() {
        ApartmentDetails actual = queryApartmentRepository.findById(apartmentId2);

        ApartmentReadModelAssertion.assertThat(actual.getApartment())
                .hasIdEqualsTo(apartmentId2)
                .hasOwnerIdEqualsTo(OWNER_ID_2)
                .hasDescriptionEqualsTo(DESCRIPTION_2)
                .hasAddressEqualsTo(STREET_2, POSTAL_CODE_2, HOUSE_NUMBER_2, APARTMENT_NUMBER_2, CITY_2, COUNTRY_2)
                .hasRoomsEqualsTo(SPACES_DEFINITION_2);
        Assertions.assertThat(actual.getBookingHistory()).isNull();
    }

    @Test
    @Transactional
    void shouldReturnApartmentWithHistory() {
        ApartmentDetails actual = queryApartmentRepository.findById(apartmentId1);

        ApartmentReadModelAssertion.assertThat(actual.getApartment())
                .hasIdEqualsTo(apartmentId1)
                .hasOwnerIdEqualsTo(OWNER_ID_1)
                .hasDescriptionEqualsTo(DESCRIPTION_1)
                .hasAddressEqualsTo(STREET_1, POSTAL_CODE_1, HOUSE_NUMBER_1, APARTMENT_NUMBER_1, CITY_1, COUNTRY_1)
                .hasRoomsEqualsTo(SPACES_DEFINITION_1);
        ApartmentBookingHistoryReadModelAssertion.assertThat(actual.getBookingHistory())
                .hasApartmentIdEqualsTo(apartmentId1)
                .hasOneApartmentBooking()
                .hasApartmentBookingFor(BOOKING_DATE_TIME_1, OWNER_ID_1, TENANT_ID_1, BOOKING_START_1, BOOKING_END_1);
    }
}