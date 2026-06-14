package com.sujan.accident.analytics.model.carDensity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dim_car_density")
public class CarDensity {
    @EmbeddedId
    private CarDensityId id;

    @Column(name="car_density")
    private Double carDensity;
}
