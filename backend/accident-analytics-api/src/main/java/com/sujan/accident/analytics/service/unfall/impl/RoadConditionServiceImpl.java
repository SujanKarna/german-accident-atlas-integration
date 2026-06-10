package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.RoadCondition;
import com.sujan.accident.analytics.repository.unfall.RoadConditionRepository;

import java.util.List;

public class RoadConditionServiceImpl {

    private final RoadConditionRepository repo;
    public RoadConditionServiceImpl(RoadConditionRepository repo) {
        this.repo = repo;
    }

    public List<RoadCondition> findAll() {
        return repo.findAll();
    }
}
