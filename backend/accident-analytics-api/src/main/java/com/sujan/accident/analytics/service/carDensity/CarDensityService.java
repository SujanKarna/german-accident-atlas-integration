package com.sujan.accident.analytics.service.carDensity;

import com.sujan.accident.analytics.model.carDensity.CarDensity;

import java.util.List;

public interface CarDensityService {
    List<CarDensity> getAll();

    List<CarDensity> getByYear(int year);

    List<CarDensity> getByState(String stateCode);
}
