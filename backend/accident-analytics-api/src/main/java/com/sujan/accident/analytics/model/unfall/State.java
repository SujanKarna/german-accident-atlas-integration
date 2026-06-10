package com.sujan.accident.analytics.model.unfall;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "dim_state")
@Data
public class State {
    @Id
    private String code;   // ULAND


    private String label;
}
