package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.dto.AccidentDto;
import com.sujan.accident.analytics.dto.AccidentSummaryDto;
import com.sujan.accident.analytics.dto.AccidentTrendDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccidentService {

    // ------------------------------------------------------------
    // BASIC YEAR + STATE QUERIES
    // ------------------------------------------------------------
    Integer getEarliestAccidentYear();
    Integer getEarliestYearForState(String stateCode);

    long countAccidentsByStateAndYear(String stateCode, Integer year);
    long countPedestrianAccidentsByStateAndYear(String stateCode, Integer year);
    long countPersonalInjuryAccidentsByStateAndYear(String stateCode, Integer year);


    List<AccidentTrendDto> getTrendsForState(String stateCode);


    // ------------------------------------------------------------
    // FILTERED ACCIDENT LISTS (DTO OUTPUT)
    // ------------------------------------------------------------
    Page<AccidentDto> filterAccidents(
            String stateCode,
            Integer year,
            Integer type,
            int page,
            int size
    );

    // ------------------------------------------------------------
    // KPI SUMMARY (DTO OUTPUT)
    // ------------------------------------------------------------
    AccidentSummaryDto getAccidentSummary(Integer year);


    // ------------------------------------------------------------
    // GROUPING FOR CHARTS
    // ------------------------------------------------------------
    List<Object[]> getAccidentsGroupedByState(Integer year);
    List<Object[]> getAccidentsGroupedByType(Integer year);


    // ------------------------------------------------------------
    // TOP N FATAL
    // ------------------------------------------------------------
    List<Object[]> getTopFatalAccidentsByYear(Integer year, Integer limit);


    // ------------------------------------------------------------
    // CROSS-DATASET ANALYTICS
    // ------------------------------------------------------------



    // ------------------------------------------------------------
    // MUNICIPALITY ANALYTICS
    // ------------------------------------------------------------

    List<Object[]> getAccidentsByMunicipalityInStateAndYear(String stateCode, Integer year);
}
