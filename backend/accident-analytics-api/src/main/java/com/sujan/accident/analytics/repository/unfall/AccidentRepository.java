package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.Accident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccidentRepository extends JpaRepository<Accident, Long> {

    // ------------------------------------------------------------
    // BASIC YEAR + STATE QUERIES
    // ------------------------------------------------------------

    @Query("SELECT MIN(a.year) FROM Accident a")
    Integer findEarliestYear();

    long countByStateCodeAndYear(String stateCode, Integer year);

    @Query("SELECT MIN(a.year) FROM Accident a WHERE a.stateCode = :stateCode")
    Integer findEarliestYearByState(String stateCode);

    long countByStateCodeAndYearAndIsPedestrianTrue(String stateCode, Integer year);

    @Query("""
        SELECT COUNT(a)
        FROM Accident a
        WHERE a.stateCode = :stateCode
          AND a.year = :year
          AND a.accidentCategoryCode IN (2, 3)
    """)
    long countPersonalInjury(String stateCode, Integer year);


    // ------------------------------------------------------------
    // KPI SUMMARY QUERIES (for dashboard cards)
    // ------------------------------------------------------------

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year")
    long countTotal(Integer year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentCategoryCode = 1")
    long countFatal(Integer year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentCategoryCode IN (2,3)")
    long countInjury(Integer year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentTypeCode = 3")
    long countBicycle(Integer year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.accidentTypeCode = 1")
    long countCar(Integer year);

    @Query("SELECT COUNT(a) FROM Accident a WHERE a.year = :year AND a.isPedestrian = true")
    long countPedestrian(Integer year);


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
    List<Object[]> countByStateForYear(Integer year);

    @Query("""
        SELECT a.accidentTypeCode, COUNT(a)
        FROM Accident a
        WHERE a.year = :year
        GROUP BY a.accidentTypeCode
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> countByTypeForYear(Integer year);


    // ------------------------------------------------------------
    // MUNICIPALITY GROUPING
    // ------------------------------------------------------------



    @Query("""
        SELECT a.municipalityCode, COUNT(a)
        FROM Accident a
        WHERE a.stateCode = :stateCode AND a.year = :year
        GROUP BY a.municipalityCode
        ORDER BY a.municipalityCode
    """)
    List<Object[]> countByMunicipalityInStateAndYear(String stateCode, Integer year);


    // ------------------------------------------------------------
    // FILTERS
    // ------------------------------------------------------------

    Page<Accident> findByStateCode(String stateCode, Pageable pageable);

    Page<Accident> findByYear(Integer year, Pageable pageable);
    Page<Accident> findByStateCodeAndYear(String stateCode, Integer year, Pageable pageable);

    Page<Accident> findByAccidentTypeCode(Integer accidentTypeCode, Pageable pageable);

    Page<Accident> findByStateCodeAndAccidentTypeCode(String stateCode, Integer accidentTypeCode, Pageable pageable);

    Page<Accident> findByYearAndAccidentTypeCode(Integer year, Integer accidentTypeCode, Pageable pageable);

    Page<Accident> findByStateCodeAndYearAndAccidentTypeCode(
            String stateCode,
            Integer year,
            Integer accidentTypeCode,
            Pageable pageable
    );




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
    List<Object[]> findTopFatalByYear(Integer year, Pageable pageable);
}
