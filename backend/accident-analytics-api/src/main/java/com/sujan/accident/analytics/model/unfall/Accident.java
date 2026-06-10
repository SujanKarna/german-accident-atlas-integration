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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_category_code", insertable = false, updatable = false)
    private AccidentCategory accidentCategory;

    // -----------------------------
    // ACCIDENT KIND
    // -----------------------------
    @Column(name = "accident_kind_code")
    private Integer accidentKindCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_kind_code", insertable = false, updatable = false)
    private AccidentKind accidentKind;

    // -----------------------------
    // ACCIDENT TYPE
    // -----------------------------
    @Column(name = "accident_type_code")
    private Integer accidentTypeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_type_code", insertable = false, updatable = false)
    private AccidentType accidentType;

    // -----------------------------
    // LIGHT CONDITION
    // -----------------------------
    @Column(name = "light_condition_code")
    private Integer lightConditionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "light_condition_code", insertable = false, updatable = false)
    private LightCondition lightCondition;

    // -----------------------------
    // PLAUSIBILITY
    // -----------------------------
    @Column(name = "plausibility_code")
    private Integer plausibilityCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plausibility_code", insertable = false, updatable = false)
    private PlausibilityLevel plausibilityLevel;

    // -----------------------------
    // ROAD CONDITION
    // -----------------------------
    @Column(name = "road_condition_code")
    private Integer roadConditionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_condition_code", insertable = false, updatable = false)
    private RoadCondition roadCondition;

    // -----------------------------
    // STATE
    // -----------------------------
    @Column(name = "state_code")
    private String stateCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_code", insertable = false, updatable = false)
    private State state;

    // -----------------------------
    // MUNICIPALITY
    // -----------------------------
    @Column(name = "municipality_code")
    private String municipalityCode;


//    Enum for Week Days
    private WeekDay weekDay;


//    Time Fields
    private Integer year;
    private Integer month;
    private Integer hour;

//    Coordinates
    private Double latitude;
    private Double longitude;
    private Double utmX;
    private Double utmY;

//    Participant Flags
    private Boolean isCar;
    private Boolean isMotorcycle;
    private Boolean isBicycle;
    private Boolean isPedestrian;
    private Boolean isGoodsVehicle;
    private Boolean isOthers;

}
