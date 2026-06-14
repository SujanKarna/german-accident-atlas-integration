package com.sujan.accident.analytics.service.population;

import com.sujan.accident.analytics.model.population.PopulationDensity;
import com.sujan.accident.analytics.repository.population.PopulationDensityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PopulationDensityServiceImpl implements PopulationDensityService {
    private final PopulationDensityRepository repo;


    @Override
    public List<PopulationDensity> getAll() {
        return repo.findAll();
    }

    @Override
    public List<PopulationDensity> getByYear(int year) {
        return repo.findByIdYear(year);
    }

    @Override
    public List<PopulationDensity> getByState(String stateCode) {
        return repo.findByIdStateCode(stateCode);
    }
}
