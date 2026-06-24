package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.LightCondition;
import com.sujan.accident.analytics.repository.unfall.LightConditionRepository;
import com.sujan.accident.analytics.service.unfall.LightConditionService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LightConditionServiceImpl implements LightConditionService {

    private final LightConditionRepository repo;
    private Map<Integer, String> cache;

    @PostConstruct
    public void loadCache() {
        cache = repo.findAll().stream()
                .collect(Collectors.toMap(
                        LightCondition::getLightConditionCode,
                        LightCondition::getLabel
                ));
    }

    @Override
    public String getLabel(Integer code) {
        if (code == null) return "Unknown";
        return cache.getOrDefault(code, "Unknown");
    }
}
