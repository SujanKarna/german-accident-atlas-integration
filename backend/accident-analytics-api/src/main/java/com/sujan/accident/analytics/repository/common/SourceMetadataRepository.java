package com.sujan.accident.analytics.repository.common;

import com.sujan.accident.analytics.model.common.SourceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceMetadataRepository extends JpaRepository<SourceMetadata, String> {
}
