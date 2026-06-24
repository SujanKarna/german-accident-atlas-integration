package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.RoadCondition;
import com.sujan.accident.analytics.repository.unfall.RoadConditionRepository;
import com.sujan.accident.analytics.service.unfall.RoadConditionService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoadConditionServiceImpl  implements RoadConditionService {

    private final RoadConditionRepository repo;


    private Map<Integer, String> cache;

    @PostConstruct
    public void loadCache() {
        cache = repo.findAll().stream()
                .collect(Collectors.toMap(
                        RoadCondition::getRoadConditionCode,
                        RoadCondition::getLabel
                ));
    }

    @Override
    public String getLabel(Integer code) {
        if (code == null) return "Unknown";
        return cache.getOrDefault(code, "Unknown");
    }
}
