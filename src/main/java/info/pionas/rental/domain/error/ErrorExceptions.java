package info.pionas.rental.domain.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorExceptions extends RuntimeException {

    private final List<RuntimeException> exceptions;
}
