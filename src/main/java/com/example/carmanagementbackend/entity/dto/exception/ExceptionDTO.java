package com.example.carmanagementbackend.entity.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}