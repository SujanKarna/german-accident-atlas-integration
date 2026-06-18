package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.Accident;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccidentRepository extends JpaRepository<Accident, Long> {

    // ------------------------------------------------------------
    // BASIC YEAR + STATE QUERIES
    // ------------------------------------------------------------

    @Query("SELECT MIN(a.year) FROM Accident a")
    int findEarliestYear();

    long countByStateCodeAndYear(String stateCode, int year);

    @Query("SELECT MIN(a.year) FROM Accident a WHERE a.stateCode = :stateCode")
    int findEarliestYearByState(String stateCode);

    long countByStateCodeAndYearAndIsPedestrianTrue(String stateCode, int year);

    @Query("""
        SELECT COUNT(a)
        FROM Accident a
        WHERE a.stateCode = :stateCode
          AND a.year = :year
          AND a.accidentCategoryCode IN (2, 3)
    """)
    long countByStateCodeAndYearAndIsPersonalInjuryTrue(String stateCode, int year);


    // ------------------------------------------------------------
    // KPI SUMMARY QUERIES (for dashboard cards)
    // ------------------------------------------------------------

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year")
    long countTotal(int year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentCategoryCode = 1")
    long countFatal(int year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentCategoryCode IN (2,3)")
    long countInjury(int year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentTypeCode = 3")
    long countBicycle(int year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentTypeCode = 1")
    long countCar(int year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.isPedestrian = true")
    long countPedestrian(int year);


    // ------------------------------------------------------------
    // GROUPING FOR CHARTS
    // ------------------------------------------------------------

    @Query("""
        SELECT a.stateCode, COUNT(a)
        FROM Accident a
        WHERE a.year = :year
        GROUP BY a.stateCode
        ORDER BY a.stateCode
    """)
    List<Object[]> countByStateForYear(int year);

    @Query("""
        SELECT a.accidentTypeCode, COUNT(a)
        FROM Accident a
        WHERE a.year = :year
        GROUP BY a.accidentTypeCode
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> countByTypeForYear(int year);


    // ------------------------------------------------------------
    // MUNICIPALITY GROUPING
    // ------------------------------------------------------------

    @Query("""
        SELECT a.municipalityCode, COUNT(a)
        FROM Accident a
        WHERE a.stateCode = :stateCode
        GROUP BY a.municipalityCode
        ORDER BY a.municipalityCode
    """)
    List<Object[]> countByMunicipalityInState(String stateCode);

    @Query("""
        SELECT a.municipalityCode, COUNT(a)
        FROM Accident a
        WHERE a.stateCode = :stateCode AND a.year = :year
        GROUP BY a.municipalityCode
        ORDER BY a.municipalityCode
    """)
    List<Object[]> countByMunicipalityInStateAndYear(String stateCode, int year);


    // ------------------------------------------------------------
    // FILTERS
    // ------------------------------------------------------------

    List<Accident> findByYear(int year);

    List<Accident> findByStateCode(String stateCode);

    List<Accident> findByStateCodeAndYear(String stateCode, int year);

    List<Accident> findByStateCodeAndYearAndAccidentType(String stateCode, int year, String accidentType);


    // ------------------------------------------------------------
    // TOP N FATAL
    // ------------------------------------------------------------

    @Query("""
        SELECT a.municipalityCode, COUNT(a)
        FROM Accident a
        WHERE a.year = :year AND a.accidentCategoryCode = 1
        GROUP BY a.municipalityCode
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findTopFatalByYear(int year, Pageable pageable);
}
