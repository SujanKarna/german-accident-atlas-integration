package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.model.unfall.LightCondition;
import com.sujan.accident.analytics.model.unfall.PlausibilityLevel;

import java.util.List;

public interface PlausibilityLevelService {
    List<PlausibilityLevel> findAll();
}
