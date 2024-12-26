package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.exception.ExceptionDTO;
import com.example.carmanagementbackend.entity.excpetion.CarNotFoundException;
import com.example.carmanagementbackend.entity.excpetion.GarageNotFoundException;
import com.example.carmanagementbackend.entity.excpetion.MaintenanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(exception = {GarageNotFoundException.class, CarNotFoundException.class, MaintenanceNotFoundException.class})
    public ResponseEntity<ExceptionDTO> handleEntityNotFoundExceptions(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();

        exceptionDTO.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDTO.setMessage(exception.getMessage());
        exceptionDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionDTO);
    }

    @ExceptionHandler(exception = {MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionDTO> handleInvalidInputExceptions(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();

        exceptionDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDTO.setMessage(exception.getMessage());
        exceptionDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDTO);
    }
}
