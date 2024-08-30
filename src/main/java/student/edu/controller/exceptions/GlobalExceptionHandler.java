package student.edu.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(IllegalArgumentException businessException, HttpServletRequest request){
        log.error("API ERROR - ", businessException);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,
                businessException.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NoSuchElementException notFoundException, HttpServletRequest request){
        log.error("API ERROR - ", notFoundException);
        return new ResponseEntity<>(new ErrorMessage(request, HttpStatus.NOT_FOUND,  notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                          HttpServletRequest request, BindingResult result) {
        log.error("API error - ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(
                new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s):", result)
        );
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> handleUnexpectedException(Throwable unexpectedException, HttpServletRequest request){
        var message = "Unexpected server error occurred, see the logs!";
        log.error(message, unexpectedException);
        return new ResponseEntity<>(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
