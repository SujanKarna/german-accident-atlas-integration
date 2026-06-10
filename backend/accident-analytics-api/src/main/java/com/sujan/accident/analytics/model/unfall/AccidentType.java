package com.sujan.accident.analytics.model.unfall;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="dim_accident_type")
public class AccidentType {
    @Id
    private Integer id;
    private String label;
}
