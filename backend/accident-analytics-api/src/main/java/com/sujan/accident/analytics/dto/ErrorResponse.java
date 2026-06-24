package com.sujan.accident.analytics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard error response returned by the API")
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error message describing the issue", example = "Invalid state code: XX")
    private String message;

    @Schema(description = "Timestamp of the error", example = "2026-06-19T13:45:00Z")
    private String timestamp;
}
