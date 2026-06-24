package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.model.unfall.State;

import java.util.List;

public interface StateService {

    boolean exists(String stateCode);

    String getStateName(String stateCode);

}
