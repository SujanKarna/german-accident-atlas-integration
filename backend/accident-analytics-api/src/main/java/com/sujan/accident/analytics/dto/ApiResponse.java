package com.sujan.accident.analytics.dto;

import com.sujan.accident.analytics.model.common.SourceMetadata;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final T data;
    private final SourceMetadata sourceMetadata;

    public ApiResponse(T data, SourceMetadata sourceMetadata) {
        this.data = data;
        this.sourceMetadata = sourceMetadata;
    }


}
