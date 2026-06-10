package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.model.unfall.AccidentKind;
import com.sujan.accident.analytics.repository.unfall.AccidentKindRepository;

import java.util.List;

public interface AccidentKindService {
    List<AccidentKind> findAll();

}
