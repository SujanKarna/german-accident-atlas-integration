package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dim_accident_category")
public class AccidentCategory {
    @Id
    private Integer categoryCode;
    private String label;
}
