package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.model.unfall.AccidentCategory;
import com.sujan.accident.analytics.repository.unfall.AccidentCategoryRepository;
import com.sujan.accident.analytics.service.unfall.AccidentCategoryService;

import java.util.List;

public class AccidentCategoryServiceImpl implements AccidentCategoryService {

    private final AccidentCategoryRepository repo;

    public AccidentCategoryServiceImpl(AccidentCategoryRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<AccidentCategory> findAll() {
        return repo.findAll();
    }
}
