package com.sujan.accident.analytics.dto;

import com.sujan.accident.analytics.model.common.SourceMetadata;
import lombok.Getter;

@Getter
public class ApiResponseDto<T> {
    private final String message;
    private final T data;
    private final SourceMetadata sourceMetadata;

    public ApiResponseDto(String message, T data, SourceMetadata sourceMetadata) {
        this.message =  message;
        this.data = data;
        this.sourceMetadata = sourceMetadata;
    }


}
