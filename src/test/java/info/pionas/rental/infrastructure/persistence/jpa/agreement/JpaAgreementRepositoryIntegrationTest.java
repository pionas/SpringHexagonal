package info.pionas.rental.infrastructure.persistence.jpa.agreement;

import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.agreement.AgreementRepository;
import info.pionas.rental.domain.money.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.agreement.Agreement.Builder.agreement;
import static info.pionas.rental.domain.rentalplaceidentifier.RentalType.APARTMENT;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaAgreementRepositoryIntegrationTest {
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID = "5678";
    private static final String OWNER_ID = "1982";
    private static final Money PRICE = Money.of(BigDecimal.valueOf(42));
    public static final LocalDate TODAY = LocalDate.now();
    private static final List<LocalDate> DAYS = asList(TODAY, TODAY.plusDays(1));

    @Autowired
    private AgreementRepository agreementRepository;
    @Autowired
    private SpringJpaAgreementTestRepository springJpaAgreementTestRepository;
    private UUID agreementId;

    @AfterEach
    void deleteAgreements() {
        if (agreementId != null) {
            springJpaAgreementTestRepository.deleteById(agreementId);
        }
    }

    @Test
    void shouldSaveAgreement() {
        agreementId = agreementRepository.save(createAgreement());
        Agreement actual = agreementRepository.findById(agreementId);

        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("rentalPlaceId", RENTAL_PLACE_ID);
    }

    @Test
    void shouldThrowExceptionWhenApartmentDoesNotExist() {
        UUID nonExistingAgreementId = UUID.randomUUID();

        AgreementDoesNotExistException actual = assertThrows(AgreementDoesNotExistException.class, () -> {
            agreementRepository.findById(nonExistingAgreementId);
        });

        assertThat(actual).hasMessage("Agreement with id " + nonExistingAgreementId + " does not exist");
    }

    private Agreement createAgreement() {
        return agreement()
                .withRentalType(APARTMENT)
                .withRentalPlaceId(RENTAL_PLACE_ID)
                .withOwnerId(OWNER_ID)
                .withTenantId(TENANT_ID)
                .withDays(DAYS)
                .withPrice(PRICE)
                .build();
    }
}