package com.example.klasha.api;

import com.example.klasha.api.models.app.Response;
import com.example.klasha.exceptions.BadRequestException;
import com.example.klasha.exceptions.Error;
import com.example.klasha.exceptions.NotFoundException;
import com.example.klasha.exceptions.RemoteServerException;
import com.example.klasha.exceptions.WebServiceException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;


import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        Response response = new Response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                messageSource.getMessage("exception.500", null, LocaleContextHolder.getLocale()), null);
        return ResponseEntity.internalServerError().body(response);
    }
    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<Response>  handleWebServiceException(WebServiceException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        Response response = new Response(String.valueOf(httpStatus),
                e.getMessage(), null);
        return ResponseEntity.status(httpStatus).body(response);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response>  handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("MissingServletRequestParameterException.message", null,
                        LocaleContextHolder.getLocale()), null);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response>  handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("HttpMessageNotReadableException.message", null,
                        LocaleContextHolder.getLocale()), null);
        return ResponseEntity.status(status).body(response);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("NotFoundException.message", e.getArgs(),
                        LocaleContextHolder.getLocale()), null);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(RemoteServerException.class)
    public ResponseEntity<Response> handleRemoteServerException(RemoteServerException e) {
        if(Objects.nonNull(e.getMessage())){
            log.error(e.getMessage());
        }else{
            log.error(messageSource.getMessage("RemoteServerException.message", e.getArgs(),
                    LocaleContextHolder.getLocale()));
        }
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("RemoteServerException.message", e.getArgs(),
                        LocaleContextHolder.getLocale()), null);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException e) {
        HttpStatus status = e.getStatus();
        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("BadRequestException.message", e.getArgs(),
                        LocaleContextHolder.getLocale()), null);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<com.example.klasha.exceptions.Error> errors = new ArrayList<>();

        List<String> errorField =ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getField).collect(Collectors.toList());

        for(int i=0; i<ex.getAllErrors().size(); i++){
            errors.add(new Error(errorField.get(i), ex.getAllErrors().get(i).getDefaultMessage()));
        }


        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("MethodArgumentNotValidException.message", null,
                        LocaleContextHolder.getLocale()), errors);
        return ResponseEntity.status(status).body(response);
    }
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, List<String>>> handleConstraintViolation(MethodArgumentNotValidException e) {
//        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
//
//        List<String> errors = e.getBindingResult().getFieldErrors()
//                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
//        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    private Map<String, List<String>> getErrorsMap(List<String> errors) {
//        Map<String, List<String>> errorResponse = new HashMap<>();
//        errorResponse.put("errors", errors);
//        return errorResponse;
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleBadRequestException(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<com.example.klasha.exceptions.Error> errorRec = e.getConstraintViolations().stream()
                .map(ex -> new Error(ex.getPropertyPath().toString(), e.getMessage()))
                .toList();
        log.info("message {}", e.getMessage());
        Response response = new Response(String.valueOf(status.value()),
                messageSource.getMessage("ConstraintViolationException.message", null,
                        LocaleContextHolder.getLocale()), errorRec);
        return ResponseEntity.status(status).body(response);
    }

}