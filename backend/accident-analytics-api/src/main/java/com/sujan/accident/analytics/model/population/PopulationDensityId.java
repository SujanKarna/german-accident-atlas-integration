package com.sujan.accident.analytics.model.population;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PopulationDensityId implements Serializable {
    @Column(name= "state_code")
    private String stateCode;

    @Column(name = "year")
    private Integer year;
}
