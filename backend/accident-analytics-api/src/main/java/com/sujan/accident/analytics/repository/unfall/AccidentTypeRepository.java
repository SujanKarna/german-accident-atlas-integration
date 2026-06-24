package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.AccidentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentTypeRepository extends JpaRepository<AccidentType, Integer> {
}
