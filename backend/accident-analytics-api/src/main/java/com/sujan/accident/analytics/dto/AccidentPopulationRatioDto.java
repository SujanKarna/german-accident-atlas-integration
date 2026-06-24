package com.sujan.accident.analytics.dto;

public record AccidentPopulationRatioDto(
        String stateCode,
        String stateName,
        int accidents,
        double populationDensity,
        double ratio) {
}
