package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingAssertion;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryAssertion;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import info.pionas.rental.domain.period.Period;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaApartmentBookingHistoryRepositoryIntegrationTest {
    @Autowired
    private ApartmentBookingHistoryRepository repository;
    @Autowired
    private SpringJpaApartmentBookingHistoryRepository jpaRepository;
    private String apartmentId;

    @AfterEach
    void deleteApartmentBookingHistory() {
        if (apartmentId != null) {
            jpaRepository.deleteById(apartmentId);
        }
    }

    @Test
    void shouldRecognizeApartmentBookingHistoryDoesNotExist() {
        String id = randomId();

        assertThat(repository.existsFor(id)).isFalse();
    }

    @Test
    void shouldRecognizeApartmentBookingHistoryExists() {
        String id = randomId();
        repository.save(new ApartmentBookingHistory(id));

        assertThat(repository.existsFor(id)).isTrue();
    }

    @Test
    @Transactional
    void shouldFindExistingApartmentBookingHistory() {
        apartmentId = randomId();
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 10);
        LocalDateTime eventCreationDate = LocalDateTime.now();
        String ownerId = randomId();
        String tenantId = randomId();
        ApartmentBookingHistory apartmentBookingHistory = new ApartmentBookingHistory(apartmentId);
        apartmentBookingHistory.addBookingStart(eventCreationDate, ownerId, tenantId, new Period(start, end));
        repository.save(apartmentBookingHistory);

        ApartmentBookingHistory actual = repository.findFor(apartmentId);

        ApartmentBookingHistoryAssertion.assertThat(actual)
                .hasOneApartmentBooking()
                .hasApartmentBookingThatSatisfies(actualBooking -> {
                    ApartmentBookingAssertion.assertThat(actualBooking)
                            .hasOwnerIdEqualTo(ownerId)
                            .hasTenantIdEqualTo(tenantId)
                            .hasPeriodThatHas(start, end);
                });
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }
}