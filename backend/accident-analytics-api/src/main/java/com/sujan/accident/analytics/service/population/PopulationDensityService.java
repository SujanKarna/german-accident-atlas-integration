package com.sujan.accident.analytics.service.population;


import com.sujan.accident.analytics.model.population.PopulationDensity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PopulationDensityService {

    List<PopulationDensity> getAll();

    List<PopulationDensity> getByYear(int year);

    List<PopulationDensity> getByState(String stateCode);
}
