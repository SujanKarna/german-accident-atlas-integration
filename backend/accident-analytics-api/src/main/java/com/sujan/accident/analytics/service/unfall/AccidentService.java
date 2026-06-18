package com.sujan.accident.analytics.service.unfall;


import java.util.List;
import java.util.Map;

public interface AccidentService {

    int getEarliestAccidentYear();
    int getEarliestYearForState(String stateCode);
    long countAccidentsByStateAndYear(String state, int year);
    long countPedestrianAccidentsByStateAndYear(String stateCode, int year);
    long countPersonalInjuryAccidentsByStateAndYear(String stateCode, int year);

    List<?> getAccidentsByYear(int year);
    List<?> getAccidentsByState(String stateCode);
    List<?> getAccidentsByStateAndYear(String stateCode, int year);
    List<?> getAccidentsByStateYearAndType(String stateCode, int year, String type);

    Map<String, Object> getAccidentSummary(int year);

    long countByYear(int year);
    List<Object[]> getAccidentsGroupedByState(int year);
    List<Object[]> getAccidentsGroupedByType(int year);

    List<Object[]> getTopFatalAccidentsByYear(int year, int limit);

    double calculateAccidentsPer100kCars(String stateCode, int year);
    double calculateAccidentsPerKm2(String stateCode, int year);
    double calculateAccidentsPerCapita(String stateCode, int year);

    List<Object[]> getAccidentsByMunicipalityInState(String stateCode);
    List<Object[]> getAccidentsByMunicipalityInStateAndYear(String stateCode, int year);

}
