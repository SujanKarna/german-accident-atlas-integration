package com.sujan.accident.analytics.model.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "source_metadata")
@Data
public class SourceMetadata {
    @Id
    private String dataset;

    private String sourceUrl;
    private String downloadedAt;
    private String sha256;

    private String license;
    private String licenseUrl;
}
