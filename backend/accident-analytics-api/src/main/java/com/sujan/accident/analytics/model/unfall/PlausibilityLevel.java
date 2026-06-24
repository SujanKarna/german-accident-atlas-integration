package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="dim_plausibility_level")
public class PlausibilityLevel {
    @Id
    private Integer plausibilityCode;

    private String label;
}
