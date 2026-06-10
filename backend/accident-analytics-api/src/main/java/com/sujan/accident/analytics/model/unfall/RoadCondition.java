package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dim_road_condition")
public class RoadCondition {
    @Id
    private Integer code;
    private String label;

    @Entity
    @Data
    @Table(name = "dim_district")
    public static class district {
        @Id
        private String agsDistrict;

        private String districtName;
        private String stateName;
        private String stateCode;
    }
}
