package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.model.unfall.State;

import java.util.List;

public interface StateService {

    List<State> getAllStates();

    State getStateByCode(String code);

    String getStateName(String code);

    boolean existsStateByCode(String code);

}
