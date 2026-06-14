package com.sujan.accident.analytics.repository.population;

import com.sujan.accident.analytics.model.population.PopulationDensity;
import com.sujan.accident.analytics.model.population.PopulationDensityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopulationDensityRepository extends JpaRepository<PopulationDensity, PopulationDensityId> {

    List<PopulationDensity> findByIdYear(Integer year);

    List<PopulationDensity> findByIdStateCode(String stateCode);
}
