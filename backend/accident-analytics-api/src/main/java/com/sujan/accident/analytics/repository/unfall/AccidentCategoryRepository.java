package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.AccidentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentCategoryRepository extends JpaRepository<AccidentCategory, Integer> {
}
