package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.repository.carDensity.CarDensityRepository;
import com.sujan.accident.analytics.repository.population.PopulationDensityRepository;
import com.sujan.accident.analytics.repository.unfall.AccidentRepository;
import com.sujan.accident.analytics.repository.unfall.StateRepository;
import com.sujan.accident.analytics.service.unfall.AccidentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository repo;
    private final CarDensityRepository carRepo;
    private final PopulationDensityRepository populationRepo;


    // Mandatory DBW questions
    @Override
    public int getEarliestAccidentYear() {

        return repo.findEarliestYear();
    }

    @Override
    public int getEarliestYearForState(String stateCode) {

        return repo.findEarliestYearByState(stateCode);
    }

    @Override
    public long countAccidentsByStateAndYear(String stateCode, int year) {
        return repo.countByStateCodeAndYear(stateCode, year);
    }

    @Override
    public long countPedestrianAccidentsByStateAndYear(String stateCode, int year) {
        return repo.countByStateCodeAndYearAndIsPedestrianTrue(stateCode, year);
    }

    @Override
    public long countPersonalInjuryAccidentsByStateAndYear(String stateCode, int year) {
        return repo.countByStateCodeAndYearAndIsPersonalInjuryTrue(stateCode, year);
    }

    // Table filtering
    @Override
    public List<?> getAccidentsByYear(int year) {
        return repo.findByYear(year);
    }

    @Override
    public List<?> getAccidentsByState(String stateCode) {
        return repo.findByStateCode(stateCode);
    }

    @Override
    public List<?> getAccidentsByStateAndYear(String stateCode, int year) {
        return repo.findByStateCodeAndYear(stateCode, year);
    }

    @Override
    public List<?> getAccidentsByStateYearAndType(String stateCode, int year, String type) {
        return repo.findByStateCodeAndYearAndAccidentType(stateCode, year, type);
    }
    // Dashboard summary
    @Override
    public Map<String, Object> getAccidentSummary(int year) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("year", year);
        summary.put("totalAccidents", repo.countTotal(year));
        summary.put("fatalAccidents", repo.countFatal(year));
        summary.put("injuryAccidents", repo.countInjury(year));
        summary.put("bicycleAccidents", repo.countBicycle(year));
        summary.put("carAccidents", repo.countCar(year));
        summary.put("pedestrianAccidents", repo.countPedestrian(year));

        return summary;
    }

    @Override
    public long countByYear(int year) {
        return repo.countTotal(year);
    }
    // Grouped analytics
    @Override
    public List<Object[]> getAccidentsGroupedByState(int year) {
        return repo.countByStateForYear(year);
    }

    @Override
    public List<Object[]> getAccidentsGroupedByType(int year) {
        return repo.countByTypeForYear(year);
    }

    // Municipality-level analytics
    @Override
    public List<Object[]> getAccidentsByMunicipalityInState(String stateCode) {
        return repo.countByMunicipalityInState(stateCode);
    }

    @Override
    public List<Object[]> getAccidentsByMunicipalityInStateAndYear(String stateCode, int year) {
        return repo.countByMunicipalityInStateAndYear(stateCode, year);
    }

    // Advanced analytics
    @Override
    public List<Object[]> getTopFatalAccidentsByYear(int year, int limit) {
        return repo.findTopFatalByYear(year, PageRequest.of(0, limit));
    }

    // Cross-dataset analytics
    @Override
    public double calculateAccidentsPer100kCars(String stateCode, int year) {
        long accidents = repo.countByStateCodeAndYear(stateCode, year);
        var car = carRepo.findByIdStateCodeAndIdYear(stateCode, year);
        if (car == null || car.getCarDensity() == 0) return 0;
        return (accidents / car.getCarDensity()) * 100000.0;
    }

    @Override
    public double calculateAccidentsPerKm2(String stateCode, int year) {
        long accidents = repo.countByStateCodeAndYear(stateCode, year);
        var pop = populationRepo.findByIdStateCodeAndIdYear(stateCode, year);
        if (pop == null || pop.getPopulationDensity() == 0) return 0;
        return accidents / pop.getPopulationDensity();
    }

    @Override
    public double calculateAccidentsPerCapita(String stateCode, int year) {
        long accidents = repo.countByStateCodeAndYear(stateCode, year);
        var pop = populationRepo.findByIdStateCodeAndIdYear(stateCode, year);
        if (pop == null || pop.getPopulationDensity() == 0) return 0;
        return accidents / (pop.getPopulationDensity() * 1000.0);
    }


}
