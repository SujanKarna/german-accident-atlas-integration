package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.RoadCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadConditionRepository extends JpaRepository<RoadCondition, Long> {
}
