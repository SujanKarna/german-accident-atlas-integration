package com.sujan.accident.analytics.dto;

public record AccidentTrendDto(
        int year,
        long accidents,
        long previousYearAccidents,
        long difference,
        double percentageChange,
        String direction
) {}
