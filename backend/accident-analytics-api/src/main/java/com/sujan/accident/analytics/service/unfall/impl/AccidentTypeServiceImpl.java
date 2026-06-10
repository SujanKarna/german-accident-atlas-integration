package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.AccidentType;
import com.sujan.accident.analytics.repository.unfall.AccidentTypeRepository;
import com.sujan.accident.analytics.service.unfall.AccidentTypeService;

import java.util.List;

public class AccidentTypeServiceImpl implements AccidentTypeService {
    private AccidentTypeRepository repo;
    public AccidentTypeServiceImpl(AccidentTypeRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<AccidentType> findAll() {
        return repo.findAll();
    }
}
