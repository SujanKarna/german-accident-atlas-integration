package com.sujan.accident.analytics.repository.carDensity;

import com.sujan.accident.analytics.model.carDensity.CarDensity;
import com.sujan.accident.analytics.model.carDensity.CarDensityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarDensityRepository extends JpaRepository<CarDensity, CarDensityId> {

    List<CarDensity> findByIdYear(Integer year);

    List<CarDensity> findByIdStateCode(String stateCode);
}
