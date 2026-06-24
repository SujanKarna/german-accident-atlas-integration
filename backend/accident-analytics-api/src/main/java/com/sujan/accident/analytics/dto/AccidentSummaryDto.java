package com.sujan.accident.analytics.dto;

public record AccidentSummaryDto(Integer year,
                                 long total,
                                 long fatal,
                                 long injury,
                                 long bicycle,
                                 long car,
                                 long pedestrian) {
}
