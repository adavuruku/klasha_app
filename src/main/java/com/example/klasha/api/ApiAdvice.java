package com.example.klasha.api;

import com.example.klasha.api.models.app.Response;
import com.example.klasha.exceptions.BadRequestException;
import com.example.klasha.exceptions.NotFoundException;
import com.example.klasha.exceptions.RemoteServerException;
import com.example.klasha.exceptions.WebServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.Objects;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<Response>  handleWebServiceException(WebServiceException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        Response response = new Response(String.valueOf(httpStatus),
                e.getMessage(), null);
        return ResponseEntity.status(httpStatus).body(response);
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
}