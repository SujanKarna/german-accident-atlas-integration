package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.Accident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccidentRepository extends JpaRepository<Accident, Long> {

    @Query("SELECT MIN(a.year) FROM Accident a")
    int findEarliestYear();

    @Query("SELECT MIN(a.year) FROM Accident a")
    Integer findMinYear();

    @Query("SELECT MAX(a.year) FROM Accident a")
    Integer findMaxYear();

    long countByStateCodeAndYear(String stateCode, int year);

    @Query("SELECT MIN(a.year) FROM Accident a WHERE a.stateCode = :stateCode")
    int findEarliestYearByState(String stateCode);

    long countByStateCodeAndYearAndIsPedestrianTrue(String stateCode, int year);

    //long countByDistrict_CityNameAndYearAndAccidentType_label(String cityName, int year, String label);
}
