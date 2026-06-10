package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.State;
import com.sujan.accident.analytics.repository.unfall.StateRepository;
import com.sujan.accident.analytics.service.unfall.StateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository repo;

    @Override
    public List<State> getAllStates() {
        return repo.findAll();
    }

    @Override
    public State getStateByCode(String code) {
        return repo.findById(code).orElseThrow(()-> new RuntimeException("State not found: " + code));
    }

    @Override
    public String getStateName(String code) {
        return getStateByCode(code).getLabel();
    }

    @Override
    public boolean existsStateByCode(String code) {
        return repo.existsById(code);
    }
}
