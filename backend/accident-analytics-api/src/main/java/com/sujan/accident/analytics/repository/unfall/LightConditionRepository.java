package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.LightCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightConditionRepository extends JpaRepository<LightCondition, Long> {
}
