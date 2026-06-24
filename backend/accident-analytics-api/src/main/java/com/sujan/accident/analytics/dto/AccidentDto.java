package com.sujan.accident.analytics.dto;

public record AccidentDto(
        Long id,
        Integer year,
        Integer month,
        Integer hour,

        String stateCode,
        String stateName,

        String municipalityCode,

        Integer accidentCategoryCode,
        String accidentCategoryLabel,

        Integer accidentKindCode,
        String accidentKindLabel,

        Integer accidentTypeCode,
        String accidentTypeLabel,

        Integer lightConditionCode,
        String lightConditionLabel,

        Integer roadConditionCode,
        String roadConditionLabel,

        Integer plausibilityCode,
        String plausibilityLabel,

        String weekDay,

        Double latitude,
        Double longitude,
        Double utmX,
        Double utmY,

        Boolean isCar,
        Boolean isMotorcycle,
        Boolean isBicycle,
        Boolean isPedestrian,
        Boolean isGoodsVehicle,
        Boolean isOthers
) {}
