package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.dto.AccidentDto;
import com.sujan.accident.analytics.dto.AccidentSummaryDto;
import com.sujan.accident.analytics.dto.AccidentTrendDto;
import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.InvalidYearException;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
import com.sujan.accident.analytics.mapper.AccidentMapper;
import com.sujan.accident.analytics.model.unfall.Accident;
import com.sujan.accident.analytics.repository.unfall.AccidentRepository;
import com.sujan.accident.analytics.service.unfall.AccidentService;
import com.sujan.accident.analytics.service.unfall.StateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentMapper accidentMapper;

    private final StateService stateService;

    // ------------------------------------------------------------
    // BASIC YEAR + STATE QUERIES
    // ------------------------------------------------------------

    @Override
    public Integer getEarliestAccidentYear() {
        return accidentRepository.findEarliestYear();
    }

    @Override
    public Integer getEarliestYearForState(String stateCode) {
        validateState(stateCode);
        return accidentRepository.findEarliestYearByState(stateCode);
    }

    @Override
    public long countAccidentsByStateAndYear(String stateCode, Integer year) {
        validateState(stateCode);
        validateYear(year);
        return accidentRepository.countByStateCodeAndYear(stateCode, year);
    }

    @Override
    public long countPedestrianAccidentsByStateAndYear(String stateCode, Integer year) {
        validateState(stateCode);
        validateYear(year);
        return accidentRepository.countByStateCodeAndYearAndIsPedestrianTrue(stateCode, year);
    }

    @Override
    public long countPersonalInjuryAccidentsByStateAndYear(String stateCode, Integer year) {
        validateState(stateCode);
        validateYear(year);
        return accidentRepository.countPersonalInjury(stateCode, year);
    }

    @Override
    public List<AccidentTrendDto> getTrendsForState(String stateCode) {
        List<AccidentTrendDto> trends = new ArrayList<>();

        for (int year = 2017; year <= 2024; year++) {

            long current = accidentRepository.countByStateCodeAndYear(stateCode, year);
            long previous = accidentRepository.countByStateCodeAndYear(stateCode, year - 1);

            long diff = current - previous;
            double pct = previous == 0 ? 0 : (diff * 100.0) / previous;

            String direction = diff > 0 ? "increase" :
                    diff < 0 ? "decrease" :
                            "no change";

            trends.add(new AccidentTrendDto(
                    year,
                    current,
                    previous,
                    diff,
                    pct,
                    direction
            ));
        }

        return trends;
    }


    // ------------------------------------------------------------
    // FILTERED ACCIDENT LISTS
    // ------------------------------------------------------------

    @Override
    public Page<AccidentDto> filterAccidents(
            String stateCode,
            Integer year,
            Integer type,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // Validate inputs using your existing exceptions
        if (stateCode != null && !stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        if (year != null && (year < 2016 || year > 2025)) {
            throw new InvalidYearException(year);
        }

        Page<Accident> result;

        if (stateCode != null && year != null && type != null) {
            result = accidentRepository.findByStateCodeAndYearAndAccidentTypeCode(stateCode, year, type, pageable);
        }
        else if (stateCode != null && year != null) {
            result = accidentRepository.findByStateCodeAndYear(stateCode, year, pageable);
        }
        else if (stateCode != null && type != null) {
            result = accidentRepository.findByStateCodeAndAccidentTypeCode(stateCode, type, pageable);
        }
        else if (year != null && type != null) {
            result = accidentRepository.findByYearAndAccidentTypeCode(year, type, pageable);
        }
        else if (stateCode != null) {
            result = accidentRepository.findByStateCode(stateCode, pageable);
        }
        else if (year != null) {
            result = accidentRepository.findByYear(year, pageable);
        }
        else if (type != null) {
            result = accidentRepository.findByAccidentTypeCode(type, pageable);
        }
        else {
            throw new InvalidStateCodeException("At least one filter must be provided.");
        }

        if (result.isEmpty()) {
            throw new NoDataForYearException("No accidents found for the given filters.");
        }

        return result.map(accidentMapper::toDto);
    }





    // ------------------------------------------------------------
    // KPI SUMMARY
    // ------------------------------------------------------------

    @Override
    public AccidentSummaryDto getAccidentSummary(Integer year) {
        validateYear(year);

        return new AccidentSummaryDto(
                year,
                accidentRepository.countTotal(year),
                accidentRepository.countFatal(year),
                accidentRepository.countInjury(year),
                accidentRepository.countBicycle(year),
                accidentRepository.countCar(year),
                accidentRepository.countPedestrian(year)
        );
    }


    // ------------------------------------------------------------
    // GROUPING
    // ------------------------------------------------------------

    @Override
    public List<Object[]> getAccidentsGroupedByState(Integer year) {
        validateYear(year);
        return accidentRepository.countByStateForYear(year);
    }

    @Override
    public List<Object[]> getAccidentsGroupedByType(Integer year) {
        validateYear(year);
        return accidentRepository.countByTypeForYear(year);
    }


    // ------------------------------------------------------------
    // TOP N FATAL
    // ------------------------------------------------------------

    @Override
    public List<Object[]> getTopFatalAccidentsByYear(Integer year, Integer limit) {
        validateYear(year);
        return accidentRepository.findTopFatalByYear(year, PageRequest.of(0, limit));
    }


    // ------------------------------------------------------------
    // MUNICIPALITY
    // ------------------------------------------------------------



    @Override
    public List<Object[]> getAccidentsByMunicipalityInStateAndYear(String stateCode, Integer year) {
        validateState(stateCode);
        validateYear(year);
        return accidentRepository.countByMunicipalityInStateAndYear(stateCode, year);
    }


    // ------------------------------------------------------------
    // VALIDATION HELPERS
    // ------------------------------------------------------------

    private void validateState(String stateCode) {
        if (!stateService.exists(stateCode)) {
            throw new InvalidStateCodeException("Invalid state code: " + stateCode);
        }
    }

    private void validateYear(Integer year) {
        if (accidentRepository.countTotal(year) == 0) {
            throw new NoDataForYearException("No accident data available for year: " + year);
        }
    }


}
