package com.sujan.accident.analytics.dto;

public class AccidentDto {
    Long id;
    int year;
    int month;
    int hour;

    // dimension labels
    String state;
    String district;
    String accidentType;
    String accidentCategory;
    String accidentKind;
    String lightCondition;
    String roadCondition;
    String plausibility;

    // coordinates
    Double lat;
    Double lon;
    Double utmX;
    Double utmY;

    // participant flags
    boolean car;
    boolean motorcycle;
    boolean bicycle;
    boolean pedestrian;
    boolean goodsVehicle;
    boolean other;

}
