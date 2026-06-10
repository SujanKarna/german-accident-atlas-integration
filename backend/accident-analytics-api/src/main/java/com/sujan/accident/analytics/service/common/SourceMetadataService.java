package com.sujan.accident.analytics.service.common;

import com.sujan.accident.analytics.model.common.SourceMetadata;

public interface SourceMetadataService {
    SourceMetadata getMetadata(String dataset);
}
