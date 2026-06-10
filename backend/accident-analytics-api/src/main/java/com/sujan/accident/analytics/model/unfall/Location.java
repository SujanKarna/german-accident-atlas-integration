package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "dim_district")
public class Location {
    @Id
    private String districtCode;

    private String stateCode;
    private String adminRegionCode;
    private String municipalityCode;

//    private String districtName;
//    private String stateName;

}
