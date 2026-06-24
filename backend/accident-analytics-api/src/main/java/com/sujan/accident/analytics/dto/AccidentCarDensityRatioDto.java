package com.sujan.accident.analytics.dto;

public record AccidentCarDensityRatioDto(
        String stateCode,
        String stateName,
        int accidents,
        double carDensity,
        double ratio
) {}
