package com.pblgllgs.employeeservice.exception;

import java.time.LocalDateTime;


public record ErrorDetails(
        String path,
        String message,
        int status,
        LocalDateTime localDateTime) {
}
