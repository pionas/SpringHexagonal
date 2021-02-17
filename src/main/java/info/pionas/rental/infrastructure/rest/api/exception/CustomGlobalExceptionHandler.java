package info.pionas.rental.infrastructure.rest.api.exception;

import info.pionas.rental.domain.error.ErrorExceptions;
import info.pionas.rental.domain.tenant.TenantException;
import info.pionas.rental.domain.tenant.TenantNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@ControllerAdvice(basePackages = "info.pionas.rental.infrastructure.rest.api")
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        Map<String, Object> body = buildErrorMessage(status, errors);

        return new ResponseEntity<>(body, headers, status);
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public void constraintViolationException(HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value());
//    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<Object> notFoundException(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Map<String, Object> body = buildErrorMessage(status, asList(ex.getMessage()));
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(TenantException.class)
    public ResponseEntity<Object> conflictException(Exception ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        Map<String, Object> body = buildErrorMessage(status, asList(ex.getMessage()));
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(ErrorExceptions.class)
    public ResponseEntity<Object> conflictException(ErrorExceptions ex) {
        List<String> errors = ex.getExceptions()
                .stream()
                .map(Throwable::getMessage)
                .collect(Collectors.toList());

        HttpStatus status = HttpStatus.CONFLICT;
        Map<String, Object> body = buildErrorMessage(status, errors);
        return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> buildErrorMessage(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);
        return body;
    }
}
