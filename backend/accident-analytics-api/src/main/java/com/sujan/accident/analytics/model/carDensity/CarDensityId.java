package com.sujan.accident.analytics.model.carDensity;

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
public class CarDensityId implements Serializable {
    @Column(name="state_code")
    private String stateCode;

    @Column(name="year")
    private Integer year;
}
