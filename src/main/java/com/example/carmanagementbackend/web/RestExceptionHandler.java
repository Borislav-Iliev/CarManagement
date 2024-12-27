package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.exception.ExceptionDTO;
import com.example.carmanagementbackend.entity.excpetion.CarNotFoundException;
import com.example.carmanagementbackend.entity.excpetion.ClientException;
import com.example.carmanagementbackend.entity.excpetion.GarageNotFoundException;
import com.example.carmanagementbackend.entity.excpetion.MaintenanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({GarageNotFoundException.class, CarNotFoundException.class, MaintenanceNotFoundException.class})
    public ResponseEntity<ExceptionDTO> handleEntityNotFoundExceptions(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();

        exceptionDTO.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDTO.setMessage(exception.getMessage());
        exceptionDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionDTO);
    }

    @ExceptionHandler({ClientException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ExceptionDTO> handleBadRequestExceptions(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();

        exceptionDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDTO.setMessage(exception.getMessage());
        exceptionDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDTO);
    }

    @ExceptionHandler(exception = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        StringBuilder exceptionMessage = new StringBuilder();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        int size = fieldErrors.size();
        for (int i = 0; i < size; i++) {
            FieldError fieldError = fieldErrors.get(i);

            if (i == size - 1) {
                exceptionMessage.append(fieldError.getDefaultMessage());
                break;
            }
            exceptionMessage.append(fieldError.getDefaultMessage()).append(", ");
        }

        exceptionDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDTO.setMessage(exceptionMessage.toString());
        exceptionDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDTO);
    }
}
