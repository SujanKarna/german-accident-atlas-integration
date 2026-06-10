package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.model.unfall.LightCondition;

import java.util.List;

public interface LightConditionService {
    List<LightCondition> findAll();
}
