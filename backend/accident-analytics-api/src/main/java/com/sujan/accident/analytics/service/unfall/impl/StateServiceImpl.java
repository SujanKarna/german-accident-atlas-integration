package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.State;
import com.sujan.accident.analytics.repository.unfall.StateRepository;
import com.sujan.accident.analytics.service.unfall.StateService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository repo;


    private Map<String, String> nameCache;   // stateCode → stateName
    private Set<String> existsCache;         // stateCode set for fast existence check

    @PostConstruct
    public void loadCache() {
        var states = repo.findAll();

        nameCache = states.stream()
                .collect(Collectors.toMap(
                        State::getStateCode,
                        State::getLabel
                ));

        existsCache = states.stream()
                .map(State::getStateCode)
                .collect(Collectors.toSet());
    }

    @Override
    public String getStateName(String code) {
        if (code == null) return "Unknown";
        return nameCache.getOrDefault(code, "Unknown");
    }

    @Override
    public boolean exists(String code) {
        if (code == null) return false;
        return existsCache.contains(code);
    }
}
