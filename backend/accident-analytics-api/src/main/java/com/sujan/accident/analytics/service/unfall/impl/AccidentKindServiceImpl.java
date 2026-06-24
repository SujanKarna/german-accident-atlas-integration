package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.AccidentKind;
import com.sujan.accident.analytics.repository.unfall.AccidentKindRepository;
import com.sujan.accident.analytics.service.unfall.AccidentKindService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AccidentKindServiceImpl implements AccidentKindService {

    private final AccidentKindRepository repo;
    private Map<Integer, String> cache;


    @PostConstruct
    public void loadCache() {
        cache = repo.findAll().stream()
                .collect(Collectors.toMap(
                        AccidentKind::getKindCode,
                        AccidentKind::getLabel
                ));
    }

    @Override
    public String getLabel(Integer code) {
        if (code == null) return "Unknown";
        return cache.getOrDefault(code, "Unknown");
    }
}
