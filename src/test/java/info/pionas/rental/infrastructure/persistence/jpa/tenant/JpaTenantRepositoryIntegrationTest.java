package info.pionas.rental.infrastructure.persistence.jpa.tenant;

import info.pionas.rental.domain.tenant.TenantRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaTenantRepositoryIntegrationTest {
    private final TenantRepository repository = new JpaTenantRepository();

    @Test
    void shouldAlwaysReturnTrue() {
        assertThat(repository.existById(UUID.randomUUID().toString())).isTrue();
    }
}