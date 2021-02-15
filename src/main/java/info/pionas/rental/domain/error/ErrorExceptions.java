package info.pionas.rental.domain.error;

import java.util.List;

public class ErrorExceptions extends RuntimeException {
    public ErrorExceptions(String message) {
        super(message);
    }

    public static RuntimeException listExceptions(List<RuntimeException> exceptions) {
        String message = "";
        for (RuntimeException runtimeException : exceptions) {
            message = message.concat(runtimeException.getMessage()).concat("\n");
        }
        return new ErrorExceptions(message.substring(0, message.length() - 2));
    }
}
