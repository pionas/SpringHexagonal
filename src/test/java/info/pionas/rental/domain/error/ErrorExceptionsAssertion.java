package info.pionas.rental.domain.error;

import info.pionas.rental.domain.hotel.HotelAssertion;
import info.pionas.rental.domain.hotel.HotelRoom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorExceptionsAssertion {
    private final ErrorExceptions actual;

    public static ErrorExceptionsAssertion assertThat(ErrorExceptions actual) {
        return new ErrorExceptionsAssertion(actual);
    }


    public ErrorExceptionsAssertion hasOnlyOneException(Consumer<RuntimeException> requirements) {
        hasRuntimeExceptions(1);
        return hasRuntimeException(requirements);
    }

    public ErrorExceptionsAssertion hasRuntimeException(Consumer<RuntimeException> requirements) {
        Assertions.assertThat(actual).extracting("exceptions").satisfies(rooms -> {
            Assertions.assertThat((List<RuntimeException>) rooms).anySatisfy(requirements);
        });

        return this;
    }

    public ErrorExceptionsAssertion hasRuntimeExceptions(int expected) {
        Assertions.assertThat(actual).extracting("exceptions").satisfies(exceptions -> {
            Assertions.assertThat((List<RuntimeException>) exceptions).hasSize(expected);
        });

        return this;
    }

    public ErrorExceptionsAssertion hasLoginEqualsTo(String login) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("login", login);
        return this;
    }
}
