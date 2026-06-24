package com.sujan.accident.analytics.service.population;

import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
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
        List<PopulationDensity> data = repo.findByIdYear(year);

        if (data.isEmpty()) {
            throw new NoDataForYearException("No population density data available for year: " + year);
        }

        return data;
    }

    @Override
    public List<PopulationDensity> getByState(String stateCode) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        List<PopulationDensity> data = repo.findByIdStateCode(stateCode);

        if (data.isEmpty()) {
            throw new NoDataForYearException("No population density data available for state: " + stateCode);
        }

        return data;
    }

    @Override
    public PopulationDensity getByStateAndYear(String stateCode, int year) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        PopulationDensity data = repo.findByIdStateCodeAndIdYear(stateCode, year);

        if (data == null) {
            throw new NoDataForYearException(
                    "No population density data available for state " + stateCode + " in year " + year
            );
        }

        return data;
    }
}
