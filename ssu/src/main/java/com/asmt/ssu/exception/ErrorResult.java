package com.asmt.ssu.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResult {
    private final String errorMessage;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;
}
