package br.com.nlw.events.exception;

import br.com.nlw.events.dto.ErroCampo;
import br.com.nlw.events.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage >validationExceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erros = fieldErrors.stream()
                .map(fieldError -> new ErroCampo(
                        fieldError.getField(),
                        fieldError.getDefaultMessage())).toList();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", erros));
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorMessage> eventNotFoundExceptionHandler(EventNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(
                HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of()));
    }

    @ExceptionHandler(DuplicateDataExcption.class)
    public ResponseEntity<ErrorMessage> handleDuplicateTitleException(DuplicateDataExcption e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(HttpStatus.CONFLICT.value(), e.getMessage(), List.of()));
    }

    @ExceptionHandler(UserIndicadorNotFoundException.class)
    public ResponseEntity<ErrorMessage> userIndicadorNotFoundExceptionHandler(UserIndicadorNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(
                HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of()));
    }

    @ExceptionHandler(SubscriptionConflictException.class)
    public ResponseEntity<ErrorMessage> subscriptionConflictExceptionHandler(SubscriptionConflictException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(
                HttpStatus.CONFLICT.value(), e.getMessage(), List.of()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), List.of()));
    }

}
