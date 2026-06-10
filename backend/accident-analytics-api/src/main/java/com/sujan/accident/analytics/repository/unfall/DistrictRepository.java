package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<Location, Long> {
}
