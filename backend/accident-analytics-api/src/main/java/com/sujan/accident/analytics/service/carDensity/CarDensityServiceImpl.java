package com.sujan.accident.analytics.service.carDensity;

import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
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
        List<CarDensity> data = repo.findByIdYear(year);

        if (data.isEmpty()) {
            throw new NoDataForYearException("No car density data available for year: " + year);
        }

        return data;
    }

    @Override
    public List<CarDensity> getByState(String stateCode) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        List<CarDensity> data = repo.findByIdStateCode(stateCode);

        if (data.isEmpty()) {
            throw new NoDataForYearException("No car density data available for state: " + stateCode);
        }

        return data;
    }

    @Override
    public CarDensity getByStateAndYear(String stateCode, int year) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        CarDensity data = repo.findByIdStateCodeAndIdYear(stateCode, year);

        if (data == null) {
            throw new NoDataForYearException(
                    "No car density data available for state " + stateCode + " in year " + year
            );
        }

        return data;
    }
}
