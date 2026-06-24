package com.sujan.accident.analytics.service.common.impl;

import com.sujan.accident.analytics.dto.AccidentCarDensityRatioDto;
import com.sujan.accident.analytics.dto.AccidentPopulationRatioDto;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
import com.sujan.accident.analytics.model.carDensity.CarDensity;
import com.sujan.accident.analytics.model.population.PopulationDensity;
import com.sujan.accident.analytics.repository.unfall.AccidentRepository;
import com.sujan.accident.analytics.service.carDensity.CarDensityService;
import com.sujan.accident.analytics.service.common.CrossDatasetAnalyticsService;
import com.sujan.accident.analytics.service.population.PopulationDensityService;
import com.sujan.accident.analytics.service.unfall.StateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CrossDatasetAnalyticsServiceImpl implements CrossDatasetAnalyticsService {
    private final AccidentRepository accidentRepository;
    private final PopulationDensityService populationDensityService;
    private final CarDensityService carDensityService;
    private final StateService stateService;

    @Override
    public List<AccidentPopulationRatioDto> getAccidentPopulationRatio(int year) {

        // Accident data for the selected year
        List<Object[]> accidentCounts = accidentRepository.countByStateForYear(year);

        // Population density for the same year
        List<PopulationDensity> pop = populationDensityService.getByYear(year);

        if (accidentCounts.isEmpty()) {
            throw new NoDataForYearException("No accident data found for year " + year);
        }

        if (pop.isEmpty()) {
            throw new NoDataForYearException("No population density data found for year " + year);
        }


        Map<String, Double> popMap = pop.stream()
                .collect(Collectors.toMap(
                        p -> p.getId().getStateCode(),
                        PopulationDensity::getPopulationDensity
                ));


        return accidentCounts.stream().map(row -> {
            String state = (String) row[0];
            int accidents = ((Long) row[1]).intValue();
            double density = popMap.getOrDefault(state, 0.0);

            double ratio = density == 0 ? 0 : accidents / density;

            return new AccidentPopulationRatioDto(
                    state,
                    stateService.getStateName(state),
                    accidents,
                    density,
                    ratio
            );
        }).toList();
    }


    @Override
    public List<AccidentCarDensityRatioDto> getAccidentCarDensityRatio(int year) {

        // Accident counts
        List<Object[]> accidentCounts = accidentRepository.countByStateForYear(year);

        // Car density for the same year
        List<CarDensity> carList = carDensityService.getByYear(year);

        if (carList.isEmpty()) {
            throw new NoDataForYearException("No car density data found for year " + year);
        }

        // Map: stateCode -> carDensity
        Map<String, Double> carMap = carList.stream()
                .collect(Collectors.toMap(
                        c -> c.getId().getStateCode(),
                        CarDensity::getCarDensity
                ));

        return accidentCounts.stream().map(row -> {
            String state = (String) row[0];
            int accidents = ((Long) row[1]).intValue();
            double carDensity = carMap.getOrDefault(state, 0.0);

            double ratio = carDensity == 0 ? 0 : accidents / carDensity;

            return new AccidentCarDensityRatioDto(
                    state,
                    stateService.getStateName(state),
                    accidents,
                    carDensity,
                    ratio
            );
        }).toList();
    }

}
