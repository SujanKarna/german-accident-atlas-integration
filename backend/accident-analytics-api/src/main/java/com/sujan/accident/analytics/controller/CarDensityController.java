package com.sujan.accident.analytics.controller;

import com.sujan.accident.analytics.dto.ApiResponseDto;
import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.InvalidYearException;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
import com.sujan.accident.analytics.model.carDensity.CarDensity;
import com.sujan.accident.analytics.service.carDensity.CarDensityService;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/car-density")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@Tag(
        name = "Car Density",
        description = """
            Dataset: Number of registered passenger cars per 1,000 inhabitants
            (Stichtag: 01.01.2025).
            
            Provides car density values for German federal states by year.
            """
)
public class CarDensityController {

    private final CarDensityService service;
    private final SourceMetadataService metadataService;


    private String datasetForYear(int year) {
        return "car_density_" + year;
    }
    // ------------------------------------------------------------
    // GET ALL
    // ------------------------------------------------------------

    @Operation(
            summary = "Get all car density records",
            description = """
                Returns all car density entries available in the database.
                """
    )
    @GetMapping
    public ApiResponseDto<?> getAll() {
        List<CarDensity> data = service.getAll();
        int latestYear = data.stream()
                .map(cd -> cd.getId().getYear())
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("No car density data found"));

        var meta = metadataService.getMetadata(datasetForYear(latestYear));
        return new ApiResponseDto<>(
                "All car density records retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // GET BY YEAR
    // ------------------------------------------------------------

    @Operation(
            summary = "Get car density for a specific year",
            description = """
                Returns car density values (cars per 1,000 inhabitants)
                for all German states for the given year.
                Throws an error if the year is invalid or no data exists.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car density retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid year"),
            @ApiResponse(responseCode = "404", description = "No data found for the given year")
    })
    @GetMapping("/year/{year}")
    public ApiResponseDto<?> getByYear(
            @Parameter(description = "Year (e.g., 2025)", example = "2025")
            @PathVariable int year
    ) {
        List<CarDensity> data = service.getByYear(year);
        var meta = metadataService.getMetadata(datasetForYear(year));

        if (data.isEmpty()) {
            throw new NoDataForYearException("No car density data found for year " + year);
        }

        return new ApiResponseDto<>(
                "Car density for year " + year + " retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // GET BY STATE
    // ------------------------------------------------------------

    @Operation(
            summary = "Get car density for a specific state",
            description = """
                Returns car density values (cars per 1,000 inhabitants)
                for all years for the given German federal state.
                State code must be a two-digit numeric code (01–16).
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car density retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code"),
            @ApiResponse(responseCode = "404", description = "No data found for the given state")
    })
    @GetMapping("/state/{stateCode}")
    public ApiResponseDto<?> getByState(
            @Parameter(description = "Two-digit state code (01–16)", example = "09")
            @PathVariable String stateCode
    ) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        List<CarDensity> data = service.getByState(stateCode);
        int latestYear = data.stream()
                .map(cd -> cd.getId().getYear())
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("No car density data found"));

        var meta = metadataService.getMetadata(datasetForYear(latestYear));

        if (data.isEmpty()) {
            throw new NoDataForYearException("No car density data found for state " + stateCode);
        }

        return new ApiResponseDto<>(
                "Car density for state " + stateCode + " retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // GET BY STATE + YEAR
    // ------------------------------------------------------------

    @Operation(
            summary = "Get car density for a specific state and year",
            description = """
                Returns the car density value (cars per 1,000 inhabitants)
                for the given state and year.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car density retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code or year"),
            @ApiResponse(responseCode = "404", description = "No data found for the given state and year")
    })
    @GetMapping("/state/{stateCode}/year/{year}")
    public ApiResponseDto<?> getByStateAndYear(
            @Parameter(description = "Two-digit state code (01–16)", example = "09")
            @PathVariable String stateCode,

            @Parameter(description = "Year (e.g., 2025)", example = "2025")
            @PathVariable int year
    ) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        if (year < 2016 || year > 2025) {
            throw new InvalidYearException(year);
        }

        CarDensity data = service.getByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        if (data == null) {
            throw new NoDataForYearException("No car density found for state " + stateCode + " in year " + year);
        }

        return new ApiResponseDto<>(
                "Car density for state " + stateCode + " in " + year + " retrieved successfully.",
                data,
                meta
        );
    }
}
