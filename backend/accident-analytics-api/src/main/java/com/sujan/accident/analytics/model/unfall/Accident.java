package com.sujan.accident.analytics.model.unfall;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "fact_accident")
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -----------------------------
    // ACCIDENT CATEGORY
    // -----------------------------
    @Column(name = "accident_category_code")
    private Integer accidentCategoryCode;


    // -----------------------------
    // ACCIDENT KIND
    // -----------------------------
    @Column(name = "accident_kind_code")
    private Integer accidentKindCode;



    // -----------------------------
    // ACCIDENT TYPE
    // -----------------------------
    @Column(name = "accident_type_code")
    private Integer accidentTypeCode;



    // -----------------------------
    // LIGHT CONDITION
    // -----------------------------
    @Column(name = "light_condition_code")
    private Integer lightConditionCode;



    // -----------------------------
    // PLAUSIBILITY
    // -----------------------------
    @Column(name = "plausibility_code")
    private Integer plausibilityCode;



    // -----------------------------
    // ROAD CONDITION
    // -----------------------------
    @Column(name = "road_condition_code")
    private Integer roadConditionCode;



    // -----------------------------
    // STATE
    // -----------------------------
    @Column(name = "state_code")
    private String stateCode;

    // -----------------------------
    // MUNICIPALITY
    // -----------------------------
    @Column(name = "municipality_code")
    private String municipalityCode;


//    Enum for Week Days
    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    private WeekDay weekDay;


//    Time Fields
    private Integer year;
    private Integer month;
    private Integer hour;

//    Coordinates
    private Double latitude;
    private Double longitude;
    @Column(name = "utm_x")
    private Double utmX;
    @Column(name = "utm_y")
    private Double utmY;

//    Participant Flags
    private Boolean isCar;
    private Boolean isMotorcycle;
    private Boolean isBicycle;
    private Boolean isPedestrian;
    private Boolean isGoodsVehicle;
    private Boolean isOthers;

}
