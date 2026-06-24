package com.sujan.accident.analytics.controller;

import com.sujan.accident.analytics.dto.ApiResponseDto;
import com.sujan.accident.analytics.service.common.CrossDatasetAnalyticsService;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/analytics")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@Tag(
        name = "Cross-Dataset Analytics",
        description = "Endpoints that combine Unfallatlas accident data with population, vehicle, and other dimensional datasets to produce integrated analytical insights."
)
public class CrossDatasetAnalyticsController {

    private final CrossDatasetAnalyticsService analyticsService;
    private final SourceMetadataService metadataService;

    @GetMapping("/accident-population-ratio/{year}")
    @Operation(
            summary = "Accident-to-Population-Density Ratio (per state, per year)",
            description = """
            Computes the ratio of accidents to population density for each German state.
            
            • Uses accident data for the selected year
            • Uses population density data for the same year
            • Both datasets must contain the requested year
            
            This metric shows which states have more accidents relative to how densely populated they are.
            """
    )
    public ApiResponseDto<?> getAccidentPopulationRatio(
            @Parameter(
                    description = """
                    Year for which both accident data and population density will be used.
                    The same year must exist in both datasets.
                    Example: 2024 (accidents_2024 + population_density_2024).
                    """,
                    example = "2024"
            )
            @PathVariable int year
    ) {
        var data = analyticsService.getAccidentPopulationRatio(year);
        var meta = metadataService.getMetadata("unfallatlas_" + year);

        return new ApiResponseDto<>(
                "Accident-to-population-density ratio for year " + year + " computed successfully.",
                data,
                meta
        );
    }



    @GetMapping("/accident-car-density-ratio/{year}")
    @Operation(
            summary = "Accident-to-Car-Density Ratio (per state, per year)",
            description = """
            Computes the ratio of accidents to car density for each German state.

            • Uses accident data for the selected year
            • Uses car density data (cars per 1,000 inhabitants) for the same year
            • Both datasets must contain the requested year

            This metric shows how accident levels compare to the number of cars in each state.
            A higher ratio indicates higher accident risk relative to car density.
            """
    )
    public ApiResponseDto<?> getAccidentCarDensityRatio(
            @Parameter(
                    description = """
                    Year for which both accident data and car density will be used.
                    The same year must exist in both datasets.
                    Example: 2025.
                    """,
                    example = "2025"
            )
            @PathVariable int year
    ) {
        var data = analyticsService.getAccidentCarDensityRatio(year);
        var meta = metadataService.getMetadata("accidents_" + year);

        return new ApiResponseDto<>(
                "Accident-to-car-density ratio for year " + year + " computed successfully.",
                data,
                meta
        );
    }


}
