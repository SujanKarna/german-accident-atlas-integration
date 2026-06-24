package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.AccidentType;
import com.sujan.accident.analytics.repository.unfall.AccidentTypeRepository;
import com.sujan.accident.analytics.service.unfall.AccidentTypeService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentTypeServiceImpl implements AccidentTypeService {
    private final AccidentTypeRepository repo;
    private Map<Integer, String> cache;

    @PostConstruct
    public void loadCache() {
        cache = repo.findAll().stream()
                .collect(Collectors.toMap(
                        AccidentType::getTypeCode,
                        AccidentType::getLabel
                ));
    }

    @Override
    public String getLabel(Integer code) {
        if (code == null) return "Unknown";
        return cache.getOrDefault(code, "Unknown");
    }
}
