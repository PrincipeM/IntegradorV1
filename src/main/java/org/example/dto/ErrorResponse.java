package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for error responses.
 * 
 * Provides detailed information about errors that occur during API calls.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Timestamp when the error occurred.
     */
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    /**
     * HTTP status code.
     */
    @JsonProperty("status")
    private int status;

    /**
     * Error name/type.
     */
    @JsonProperty("error")
    private String error;

    /**
     * Detailed error message.
     */
    @JsonProperty("message")
    private String message;

    /**
     * API path where the error occurred.
     */
    @JsonProperty("path")
    private String path;
}
