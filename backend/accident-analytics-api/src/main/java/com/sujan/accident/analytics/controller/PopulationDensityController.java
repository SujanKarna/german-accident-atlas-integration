package com.sujan.accident.analytics.controller;
import com.sujan.accident.analytics.dto.ApiResponseDto;
import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
import com.sujan.accident.analytics.model.population.PopulationDensity;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import com.sujan.accident.analytics.service.population.PopulationDensityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/population-density")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@Tag(
        name = "Population Density",
        description = "Endpoints for retrieving population density data for German federal states."
)
public class PopulationDensityController {

    private final PopulationDensityService service;
    private final SourceMetadataService metadataService;



    private String datasetForYear(int year) {
        return "population_density_" + year;
    }

    // ------------------------------------------------------------
    // GET ALL
    // ------------------------------------------------------------

    @Operation(
            summary = "Get all population density records",
            description = """
                Returns all population density entries available in the database.
                Useful for debugging or loading complete datasets.
                """
    )
    @GetMapping
    public ApiResponseDto<?> getAll() {
        List<PopulationDensity> data = service.getAll();
        int latestYear = data.stream()
                .map(pd -> pd.getId().getYear())
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("No population density data found"));

        var meta = metadataService.getMetadata(datasetForYear(latestYear));
        return new ApiResponseDto<>(
                "All population density records retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // GET BY YEAR
    // ------------------------------------------------------------

    @Operation(
            summary = "Get population density for a specific year",
            description = """
                Returns population density values for all German states for the given year.
                Throws an error if the year is invalid or no data exists.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Population density retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid year"),
            @ApiResponse(responseCode = "404", description = "No data found for the given year")
    })
    @GetMapping("/year/{year}")
    public ApiResponseDto<?> getByYear(
            @Parameter(description = "Year (e.g., 2024)", example = "2024")
            @PathVariable int year
    ) {

        List<PopulationDensity> data = service.getByYear(year);
        var meta = metadataService.getMetadata(datasetForYear(year));

        if (data.isEmpty()) {
            throw new NoDataForYearException("No population density data found for year " + year);
        }

        return new ApiResponseDto<>(
                "Population density for year " + year + " retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // GET BY STATE
    // ------------------------------------------------------------

    @Operation(
            summary = "Get population density for a specific state",
            description = """
                Returns population density values for all years for the given state.
                State code must be a two-digit numeric code (01–16).
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Population density retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code"),
            @ApiResponse(responseCode = "404", description = "No data found for the given state")
    })
    @GetMapping("/state/{stateCode}")
    public ApiResponseDto<?> getByState(
            @Parameter(description = "Two-digit state code (01–16)", example = "14")
            @PathVariable String stateCode
    ) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        List<PopulationDensity> data = service.getByState(stateCode);

        int latestYear = data.stream()
                .map(cd -> cd.getId().getYear())
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("No car density data found"));

        var meta = metadataService.getMetadata(datasetForYear(latestYear));
        if (data.isEmpty()) {
            throw new NoDataForYearException("No population density data found for state " + stateCode);
        }

        return new ApiResponseDto<>(
                "Population density for state " + stateCode + " retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // GET BY STATE + YEAR
    // ------------------------------------------------------------

    @Operation(
            summary = "Get population density for a specific state and year",
            description = """
                Returns the population density value for the given state and year.
                Useful for cross-dataset analytics (e.g., accidents per km²).
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Population density retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code or year"),
            @ApiResponse(responseCode = "404", description = "No data found for the given state and year")
    })
    @GetMapping("/state/{stateCode}/year/{year}")
    public ApiResponseDto<?> getByStateAndYear(
            @Parameter(description = "Two-digit state code (01–16)", example = "14")
            @PathVariable String stateCode,

            @Parameter(description = "Year (e.g., 2024)", example = "2024")
            @PathVariable int year
    ) {
        if (!stateCode.matches("\\d{2}")) {
            throw new InvalidStateCodeException(stateCode);
        }

        PopulationDensity data = service.getByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        if (data == null) {
            throw new NoDataForYearException("No population density found for state " + stateCode + " in year " + year);
        }

        return new ApiResponseDto<>(
                "Population density for state " + stateCode + " in " + year + " retrieved successfully.",
                data,
                meta
        );
    }
}
