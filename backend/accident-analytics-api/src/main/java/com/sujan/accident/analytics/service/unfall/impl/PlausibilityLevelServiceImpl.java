package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.PlausibilityLevel;
import com.sujan.accident.analytics.repository.unfall.PlausibilityLevelRepository;
import com.sujan.accident.analytics.service.unfall.PlausibilityLevelService;

import java.util.List;

public class PlausibilityLevelServiceImpl implements PlausibilityLevelService {

    private final PlausibilityLevelRepository repo;
    public PlausibilityLevelServiceImpl(PlausibilityLevelRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<PlausibilityLevel> findAll() {
        return repo.findAll();
    }
}
