package com.sujan.accident.analytics.service.common;

import com.sujan.accident.analytics.dto.AccidentCarDensityRatioDto;
import com.sujan.accident.analytics.dto.AccidentPopulationRatioDto;

import java.util.List;

public interface CrossDatasetAnalyticsService {
    List<AccidentPopulationRatioDto> getAccidentPopulationRatio(int year);
    List<AccidentCarDensityRatioDto> getAccidentCarDensityRatio(int year);

}
