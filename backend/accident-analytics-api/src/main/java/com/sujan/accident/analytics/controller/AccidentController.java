package com.sujan.accident.analytics.controller;

import com.sujan.accident.analytics.dto.ApiResponseDto;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import com.sujan.accident.analytics.service.unfall.AccidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accidents")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Tag(
        name = "Accidents",
        description = "Endpoints for analyzing German accident data (Unfallatlas). Includes counts, filters, summaries, rankings, and cross‑dataset analytics."
)
public class AccidentController {

    private final AccidentService accidentService;
    private final SourceMetadataService metadataService;

    private String datasetForYear(int year) {
        return "unfallatlas_" + year;
    }

    // ------------------------------------------------------------
    // METADATA
    // ------------------------------------------------------------

    @Operation(
            summary = "Get metadata for a specific Unfallatlas year",
            description = """
                Returns provenance metadata for the accident dataset of the given year.
                Includes dataset name, license, license URL, source URL, download timestamp and SHA256 hash.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Metadata retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Metadata not found for this year")
    })
    @GetMapping("/metadata/{year}")
    public ApiResponseDto<?> getMetadataForYear(
            @Parameter(description = "Dataset year", example = "2023")
            @PathVariable int year
    ) {
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(meta, meta);
    }

    // ------------------------------------------------------------
    // MANDATORY DBW QUESTIONS
    // ------------------------------------------------------------

    @Operation(
            summary = "Get earliest accident year",
            description = "Returns the earliest year available in the Unfallatlas dataset."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Earliest year returned"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/earliest-year")
    public ApiResponseDto<Integer> getEarliestYear() {
        int year = accidentService.getEarliestAccidentYear();
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(year, meta);
    }

    @Operation(
            summary = "Get earliest accident year for a state",
            description = "Returns the earliest year for which accident data exists for the given federal state."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Earliest year returned"),
            @ApiResponse(responseCode = "400", description = "Invalid state code"),
            @ApiResponse(responseCode = "404", description = "No data for this state")
    })
    @GetMapping("/earliest-year/state")
    public ApiResponseDto<Integer> getEarliestYearForState(
            @Parameter(description = "State code (e.g., 06, 10, 15)", example = "04")
            @RequestParam String stateCode
    ) {
        int year = accidentService.getEarliestYearForState(stateCode);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(year, meta);
    }

    @Operation(
            summary = "Count accidents by state and year",
            description = "Returns the number of accidents in a given state and year."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Count returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/count/state")
    public ApiResponseDto<Long> countByStateAndYear(
            @Parameter(description = "State code", example = "SN") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        long data = accidentService.countAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    @Operation(
            summary = "Count pedestrian accidents",
            description = "Returns the number of accidents involving pedestrians in a given state and year."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Count returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/count/pedestrian")
    public ApiResponseDto<Long> countPedestrianAccidents(
            @Parameter(description = "State code", example = "BE") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        long data = accidentService.countPedestrianAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    @Operation(
            summary = "Count personal injury accidents",
            description = "Returns the number of accidents with personal injury in a given state and year."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Count returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/count/personal-injury")
    public ApiResponseDto<Long> countPersonalInjury(
            @Parameter(description = "State code", example = "NW") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        long data = accidentService.countPersonalInjuryAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    // ------------------------------------------------------------
    // TABLE FILTERING
    // ------------------------------------------------------------

    @Operation(
            summary = "Get all accidents for a year",
            description = "Returns all accident records for the specified year."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accidents returned"),
            @ApiResponse(responseCode = "404", description = "No accidents found for this year")
    })
    @GetMapping("/year/{year}")
    public ApiResponseDto<?> getByYear(
            @Parameter(description = "Year", example = "2022")
            @PathVariable int year
    ) {
        var data = accidentService.getAccidentsByYear(year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    @Operation(
            summary = "Get all accidents for a state",
            description = "Returns all accident records for the specified federal state."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accidents returned"),
            @ApiResponse(responseCode = "404", description = "No accidents found for this state")
    })
    @GetMapping("/state/{stateCode}")
    public ApiResponseDto<?> getByState(
            @Parameter(description = "State code", example = "BY")
            @PathVariable String stateCode
    ) {
        var data = accidentService.getAccidentsByState(stateCode);
        var meta = metadataService.getMetadata("unfallatlas");
        return new ApiResponseDto<>(data, meta);
    }

    @Operation(
            summary = "Filter accidents by state, year, and type",
            description = "Returns accidents filtered by state code, year, and accident type."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered accidents returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/filter")
    public ApiResponseDto<?> filter(
            @Parameter(description = "State code", example = "SN") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year,
            @Parameter(description = "Accident type code", example = "3") @RequestParam String type
    ) {
        var data = accidentService.getAccidentsByStateYearAndType(stateCode, year, type);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    // ------------------------------------------------------------
    // MUNICIPALITY ANALYTICS
    // ------------------------------------------------------------

    @Operation(
            summary = "Get accidents grouped by municipality",
            description = "Returns accident counts grouped by municipality for a given state."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Municipality data returned"),
            @ApiResponse(responseCode = "404", description = "No data found")
    })
    @GetMapping("/municipalities")
    public ApiResponseDto<?> getMunicipalities(
            @Parameter(description = "State code", example = "SN")
            @RequestParam String stateCode
    ) {
        var data = accidentService.getAccidentsByMunicipalityInState(stateCode);
        var meta = metadataService.getMetadata("unfallatlas");
        return new ApiResponseDto<>(data, meta);
    }

    @Operation(
            summary = "Get accidents grouped by municipality for a year",
            description = "Returns accident counts grouped by municipality for a given state and year."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Municipality data returned"),
            @ApiResponse(responseCode = "404", description = "No data found")
    })
    @GetMapping("/municipalities/year")
    public ApiResponseDto<?> getMunicipalitiesByYear(
            @Parameter(description = "State code", example = "SN") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        var data = accidentService.getAccidentsByMunicipalityInStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    // ------------------------------------------------------------
    // DASHBOARD SUMMARY
    // ------------------------------------------------------------

    @Operation(
            summary = "Get accident summary KPIs",
            description = """
                Returns key performance indicators (KPIs) for the selected year:
                - total accidents
                - fatal accidents
                - injury accidents
                - bicycle accidents
                - car accidents
                - pedestrian accidents
                
                Metadata includes dataset name, license, source URL, download timestamp and SHA256 hash.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Summary returned"),
            @ApiResponse(responseCode = "404", description = "No data for this year")
    })
    @GetMapping("/summary")
    public ApiResponseDto<?> getSummary(
            @Parameter(description = "Year", example = "2023")
            @RequestParam int year
    ) {
        var data = accidentService.getAccidentSummary(year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    // ------------------------------------------------------------
    // ADVANCED ANALYTICS
    // ------------------------------------------------------------

    @Operation(
            summary = "Get top N districts with fatal accidents",
            description = "Returns the districts with the highest number of fatal accidents for the given year."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ranking returned"),
            @ApiResponse(responseCode = "404", description = "No fatal accidents found")
    })
    @GetMapping("/top-fatal")
    public ApiResponseDto<?> getTopFatal(
            @Parameter(description = "Year", example = "2024") @RequestParam int year,
            @Parameter(description = "Number of districts to return", example = "5") @RequestParam(defaultValue = "5") int limit
    ) {
        var data = accidentService.getTopFatalAccidentsByYear(year, limit);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(data, meta);
    }

    // ------------------------------------------------------------
    // CROSS-DATASET ANALYTICS
    // ------------------------------------------------------------

    @Operation(
            summary = "Accidents per 100,000 registered cars",
            description = "Cross‑dataset metric combining Unfallatlas and car density dataset."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/rate/car-density")
    public ApiResponseDto<Double> accidentsPer100kCars(
            @Parameter(description = "State code", example = "SN") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        double rate = accidentService.calculateAccidentsPer100kCars(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(rate, meta);
    }

    @Operation(
            summary = "Accidents per km²",
            description = "Cross‑dataset metric combining Unfallatlas and population density dataset."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/rate/pop-density")
    public ApiResponseDto<Double> accidentsPerKm2(
            @Parameter(description = "State code", example = "SN") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        double rate = accidentService.calculateAccidentsPerKm2(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(rate, meta);
    }

    @Operation(
            summary = "Accidents per capita",
            description = "Cross‑dataset metric combining Unfallatlas and population dataset."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate returned"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/rate/capita")
    public ApiResponseDto<Double> accidentsPerCapita(
            @Parameter(description = "State code", example = "SN") @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023") @RequestParam int year
    ) {
        double rate = accidentService.calculateAccidentsPerCapita(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(rate, meta);
    }
}
