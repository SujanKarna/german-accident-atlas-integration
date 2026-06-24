package com.sujan.accident.analytics.service.common.impl;

import com.sujan.accident.analytics.exception.MetadataNotFoundException;
import com.sujan.accident.analytics.model.common.SourceMetadata;
import com.sujan.accident.analytics.repository.common.SourceMetadataRepository;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import org.springframework.stereotype.Service;

@Service
public class SourceMetadataServiceImpl implements SourceMetadataService {
    private final SourceMetadataRepository repo;

    SourceMetadataServiceImpl(SourceMetadataRepository repo) {
        this.repo = repo;
    }



    public SourceMetadata getMetadata(String dataset) {

        return repo.findById(dataset)
                .orElseThrow(()->new MetadataNotFoundException(dataset));
    }
}
