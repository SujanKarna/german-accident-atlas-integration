package com.sujan.accident.analytics.service.unfall.impl;


import com.sujan.accident.analytics.repository.unfall.DistrictRepository;
import com.sujan.accident.analytics.service.unfall.DistrictService;

import java.util.List;

public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repo;
    public DistrictServiceImpl(DistrictRepository repo) {
        this.repo = repo;
    }


//    @Override
//    public List<District> findAll() {
//        return repo.findAll();
//    }
}
