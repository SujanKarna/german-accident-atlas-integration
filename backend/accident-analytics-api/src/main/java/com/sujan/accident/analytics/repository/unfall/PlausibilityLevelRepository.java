package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.PlausibilityLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlausibilityLevelRepository extends JpaRepository<PlausibilityLevel, Integer> {
}
