package com.sujan.accident.analytics.service.unfall.impl;

import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.repository.unfall.AccidentRepository;
import com.sujan.accident.analytics.repository.unfall.StateRepository;
import com.sujan.accident.analytics.service.unfall.AccidentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository repo;
    private final StateRepository stateRepo;



    @Override
    public int getEarliestAccidentYear() {

        return repo.findEarliestYear();
    }

    @Override
    public long countAccidentsByStateAndYear(String stateCode, int year) {
        return repo.countByStateCodeAndYear(stateCode, year);
    }

    @Override
    public int getEarliestYearForState(String stateCode) {

        return repo.findEarliestYearByState(stateCode);
    }

    @Override
    public long countPedestrianAccidentsByStateAndYear(String stateCode, int year) {
        return repo.countByStateCodeAndYearAndIsPedestrianTrue(stateCode, year);
    }


//    @Override
//    public long countAccidentsByCityYearAndType(String cityName, int year, String type) {
//        return repo.countByDistrict_CityNameAndYearAndAccidentType_label(cityName, year, type);
//    }

}
