package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dim_light_condition")
public class LightCondition {
    @Id
    private Integer lightConditionCode;

    private String label;

}
