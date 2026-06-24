package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.PlausibilityLevel;
import com.sujan.accident.analytics.repository.unfall.PlausibilityLevelRepository;
import com.sujan.accident.analytics.service.unfall.PlausibilityLevelService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlausibilityLevelServiceImpl implements PlausibilityLevelService {

    private final PlausibilityLevelRepository repo;

    private Map<Integer, String> cache;

    @PostConstruct
    public void loadCache() {
        cache = repo.findAll().stream()
                .collect(Collectors.toMap(
                        PlausibilityLevel::getPlausibilityCode,
                        PlausibilityLevel::getLabel
                ));
    }

    @Override
    public String getLabel(Integer code) {
        if (code == null) return "Unknown";
        return cache.getOrDefault(code, "Unknown");
    }
}
