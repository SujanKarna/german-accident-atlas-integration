package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dim_accident_kind")
public class AccidentKind {
    @Id
    private Integer kindCode;
    private String label;
}
