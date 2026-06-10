package com.sujan.accident.analytics.service.unfall;



public interface AccidentService {
    int getEarliestAccidentYear();
    long countAccidentsByStateAndYear(String state, int year);
    int getEarliestYearForState(String stateCode);

    long countPedestrianAccidentsByStateAndYear(String stateCode, int year);
   // long countAccidentsByCityYearAndType(String cityName, int year, String type);

}
