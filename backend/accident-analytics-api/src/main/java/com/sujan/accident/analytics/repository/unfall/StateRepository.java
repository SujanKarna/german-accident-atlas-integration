package com.sujan.accident.analytics.repository.unfall;

import com.sujan.accident.analytics.model.unfall.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, String> {
}
