package com.sujan.accident.analytics.model.population;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "dim_population_density")
public class PopulationDensity {
    @EmbeddedId
    private PopulationDensityId id;

    @Column(name = "population_density")
    private Double populationDensity;
}
