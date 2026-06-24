package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.AccidentKind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentKindRepository extends JpaRepository<AccidentKind, Integer> {
}
