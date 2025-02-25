package br.com.hackthon.api_client.resources.exceptions;

import br.com.hackthon.api_client.services.exceptions.*;
import jakarta.servlet.http.*;
import org.springframework.dao.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){

        StandardError err = new StandardError();

        HttpStatus status = HttpStatus.NOT_FOUND;

        err.setTimeStamp(Instant.now());
        err.setError("Entity not found");
        err.setMessage(e.getMessage());
        err.setStatus(status.value());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequest(BadRequestException e, HttpServletRequest request){

        StandardError err = new StandardError();

        HttpStatus status = HttpStatus.BAD_REQUEST;

        err.setTimeStamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Bad request exception.");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidatorError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

        ValidatorError err = new ValidatorError();

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        err.setTimeStamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation Exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        for(FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> database(DataIntegrityViolationException e, HttpServletRequest request){

        StandardError err = new StandardError();

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        err.setTimeStamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Data Base Exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
