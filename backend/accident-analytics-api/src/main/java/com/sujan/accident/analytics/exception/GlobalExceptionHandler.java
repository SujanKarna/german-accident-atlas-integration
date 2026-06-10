package com.sujan.accident.analytics.exception;

import com.sujan.accident.analytics.dto.ApiResponse;
import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.InvalidYearException;
import com.sujan.accident.analytics.model.common.SourceMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class GlobalExceptionHandler {
//    @ExceptionHandler(InvalidStateCodeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleInvalidState(InvalidStateCodeException ex) {
//        return ex.getMessage();
  //  }
//
//    @ExceptionHandler(InvalidYearException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiResponse<?> handleInvalidYear(InvalidYearException ex) {
//        return ApiResponse.metadataOnly(
//                new SourceMetadata("error", ex.getMessage())
//        );
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiResponse<?> handleGeneral(Exception ex) {
//        return ApiResponse.metadataOnly(
//                new SourceMetadata("error", "Unexpected server error: " + ex.getMessage())
//        );
//    }
}
