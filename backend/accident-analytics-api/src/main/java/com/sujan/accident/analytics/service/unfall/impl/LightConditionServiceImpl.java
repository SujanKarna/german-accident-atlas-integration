package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.LightCondition;
import com.sujan.accident.analytics.repository.unfall.LightConditionRepository;
import com.sujan.accident.analytics.service.unfall.LightConditionService;

import java.util.List;

public class LightConditionServiceImpl implements LightConditionService {

    private final LightConditionRepository repo;
    public LightConditionServiceImpl(LightConditionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<LightCondition> findAll() {
        return repo.findAll();
    }
}
