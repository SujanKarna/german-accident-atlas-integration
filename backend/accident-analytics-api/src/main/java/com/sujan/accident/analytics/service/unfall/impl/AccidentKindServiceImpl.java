package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.AccidentKind;
import com.sujan.accident.analytics.repository.unfall.AccidentKindRepository;
import com.sujan.accident.analytics.service.unfall.AccidentKindService;

import java.util.List;

public class AccidentKindServiceImpl implements AccidentKindService {

    private AccidentKindRepository repo;
    public AccidentKindServiceImpl(AccidentKindRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<AccidentKind> findAll() {
        return repo.findAll();
    }
}
