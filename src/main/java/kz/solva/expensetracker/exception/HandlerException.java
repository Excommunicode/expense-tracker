package kz.solva.expensetracker.exception;

import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> onNotFoundException(final NotFoundException exception) {

        log.warn("Exception: {}, Not found: \n{}", exception.getClass().getName(), getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.NOT_FOUND.name())
                .reason("The required object was not found. ")
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {

        log.warn("Exception: {}, MethodArgumentNotValidException error(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> onBadRequestException(final BadRequestException exception) {

        log.warn("Exception: {}, BadRequestException error(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ApiError> onDataConflictException(final DataConflictException exception) {

        log.warn("DataConflictException: {}, message(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.CONFLICT.name())
                .reason("Integrity constraint has been violated.")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class, Exception.class})
    public ResponseEntity<ApiError> onThrowableException(final Exception exception) {

        log.error("Exception: {}, message(s): \n{}", exception.getClass().getName(), getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiError> onAuthException(final AuthException exception) {
        log.warn("Exception: {}, AuthException error: \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(List.of(exception.getMessage()))
                .status(HttpStatus.UNAUTHORIZED.name())
                .reason("Authentication failed.")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    private String getExceptionMessage(Throwable exception) {

        return Arrays.stream(exception.getMessage().split("&"))
                .map(message -> "- " + message.trim())
                .collect(Collectors.joining("\n"));
    }

    private List<String> convertStackTraceToStringList(Throwable exception) {

        return Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
    }
}
