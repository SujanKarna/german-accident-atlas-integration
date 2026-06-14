package com.sujan.accident.analytics.controller;



import com.sujan.accident.analytics.model.carDensity.CarDensity;
import com.sujan.accident.analytics.service.carDensity.CarDensityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-density")
@AllArgsConstructor
public class CarDensityController {
    private final CarDensityService service;

    @GetMapping
    public List<CarDensity> getAll() {
        return service.getAll();
    }

    @GetMapping("/year/{year}")
    public List<CarDensity> getByYear(@PathVariable int year) {
        return service.getByYear(year);
    }

    @GetMapping("/state/{stateCode}")
    public List<CarDensity> getByState(@PathVariable String stateCode) {
        return service.getByState(stateCode);
    }
}
