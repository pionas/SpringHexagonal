package info.pionas.rental.infrastructure.persistence.jpa.owner;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("DomainRepositoryIntegrationTest")
class JpaOwnerRepositoryTest {
    private static final String ANYTHING = UUID.randomUUID().toString();

    private final JpaOwnerRepository repository = new JpaOwnerRepository();

    @Test
    void shouldAlwaysRecognizeOwnerAsExisting() {
        assertThat(repository.exists(ANYTHING)).isTrue();
    }
}