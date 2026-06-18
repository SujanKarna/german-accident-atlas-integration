package com.sujan.accident.analytics.controller;


import com.sujan.accident.analytics.model.population.PopulationDensity;
import com.sujan.accident.analytics.service.population.PopulationDensityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/population-density")
@AllArgsConstructor
public class PopulationDensityController {
    private final PopulationDensityService service;


    @GetMapping
    public List<PopulationDensity> getAll() {
        return service.getAll();
    }

    @GetMapping("/year/{year}")
    public List<PopulationDensity> getByYear(@PathVariable int year) {
        return service.getByYear(year);
    }

    @GetMapping("/state/{stateCode}")
    public List<PopulationDensity> getByState(@PathVariable String stateCode) {
        return service.getByState(stateCode);
    }
}
