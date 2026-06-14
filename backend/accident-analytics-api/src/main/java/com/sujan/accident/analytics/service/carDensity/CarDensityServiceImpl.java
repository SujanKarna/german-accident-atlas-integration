package com.sujan.accident.analytics.service.carDensity;

import com.sujan.accident.analytics.model.carDensity.CarDensity;
import com.sujan.accident.analytics.repository.carDensity.CarDensityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarDensityServiceImpl implements CarDensityService {

    private final CarDensityRepository repo;

    @Override
    public List<CarDensity> getAll() {
        return repo.findAll();
    }

    @Override
    public List<CarDensity> getByYear(int year) {
        return repo.findByIdYear(year);
    }

    @Override
    public List<CarDensity> getByState(String stateCode) {
        return repo.findByIdStateCode(stateCode);
    }
}
