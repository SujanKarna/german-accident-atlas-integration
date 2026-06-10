package com.sujan.accident.analytics.service.unfall;

import com.sujan.accident.analytics.model.unfall.AccidentCategory;

import java.util.List;

public interface AccidentCategoryService {
    List<AccidentCategory> findAll();
}
