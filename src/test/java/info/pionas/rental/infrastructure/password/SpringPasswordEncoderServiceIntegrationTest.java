package info.pionas.rental.infrastructure.password;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("IntegrationTest")
class SpringPasswordEncoderServiceIntegrationTest {
    @Autowired
    private SpringPasswordEncoderService springPasswordEncoderService;

    @Test
    void shouldEncodePassword() {
        String actual = springPasswordEncoderService.encode("password");
        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldPasswordMatches() {
        String hash = "$2a$10$7R.X6jwDw8mkgY0/KF.16OKw0DMveeaTGs72qf9TkXlrPdhbBtkoq";
        String password = "123456";
        boolean actual = springPasswordEncoderService.matches(password, hash);
        assertThat(actual).isTrue();
    }

    @Test
    void shouldPasswordDoesNotMatches() {
        String hash = "$2a$10$7R.X6jwDw8mkgY0/KF.16OKw0DMveeaTGs72qf9TkXlrPdhbBtkoq";
        String password = "1234567";
        boolean actual = springPasswordEncoderService.matches(password, hash);
        assertThat(actual).isFalse();
    }
}