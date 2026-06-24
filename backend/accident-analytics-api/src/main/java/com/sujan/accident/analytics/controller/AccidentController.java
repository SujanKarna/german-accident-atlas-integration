package com.sujan.accident.analytics.controller;

import com.sujan.accident.analytics.dto.AccidentTrendDto;
import com.sujan.accident.analytics.dto.ApiResponseDto;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import com.sujan.accident.analytics.service.unfall.AccidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accidents")
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
            summary = "Retrieve metadata for a specific Unfallatlas dataset year",
            description = """
        Returns metadata describing the Unfallatlas dataset for the given year.
        Includes license, source URL, checksum, and download timestamp.
        Useful for verifying dataset provenance.
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Metadata retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Metadata for the given year does not exist")
    })
    @GetMapping("/metadata/{year}")
    public ApiResponseDto<?> getMetadataForYear(@Parameter(description = "Dataset year (2016–2024)", example = "2020") @PathVariable int year) {
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Metadata for dataset year " + year + " retrieved successfully.",
                meta,
                meta
        );
    }

    // ------------------------------------------------------------
    // MANDATORY DBW QUESTIONS
    // ------------------------------------------------------------

    @Operation(
            summary = "Retrieve the earliest year available in the Unfallatlas dataset",
            description = """
        Returns the first year for which accident data exists in the database.
        """
    )
    @GetMapping("/earliest-year")
    public ApiResponseDto<Integer> getEarliestYear() {
        int year = accidentService.getEarliestAccidentYear();
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Earliest accident year retrieved successfully.",
                year,
                meta
        );
    }

    @Operation(
            summary = "Retrieve the earliest accident year for a specific state",
            description = """
        Returns the earliest year in which the selected German federal state
        (Bundesland) has recorded accident data.
        """
    )
    @GetMapping("/earliest-year/state")
    public ApiResponseDto<Integer> getEarliestYearForState(
            @Parameter(description = "Two‑digit numeric state code (01–16)- Eg: 05 - North Rhine-Westphalia and 13 - Mecklenburg-Western Pomerania", example = "05")
            @RequestParam String stateCode
    ) {
        int year = accidentService.getEarliestYearForState(stateCode);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Earliest accident year for state " + stateCode + " retrieved successfully.",
                year,
                meta
        );
    }

    @Operation(
            summary = "Count accidents for a given state and year",
            description = """
        Returns the total number of accidents recorded in a specific German state
        during the selected year.
        """
    )
    @GetMapping("/count/state")
    public ApiResponseDto<Long> countByStateAndYear(
            @Parameter(description = "Numeric state code (e.g., 01, 05, 11, 14)", example = "14")
            @RequestParam String stateCode,
            @Parameter(description = "Year", example = "2023")
            @RequestParam int year
    ) {
        long count = accidentService.countAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Accident count for state " + stateCode + " in " + year + " retrieved successfully.",
                count,
                meta
        );
    }

    @Operation(
            summary = "Count pedestrian accidents for a given state and year",
            description = """
            Returns the total number of pedestrian‑involved accidents recorded in the
            specified German federal state (Bundesland) during the selected year.
            
            A pedestrian accident is defined according to Unfallatlas classification
            where at least one pedestrian was involved in the incident.
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedestrian accident count retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code or year"),
            @ApiResponse(responseCode = "404", description = "No accident data found for the given parameters")
    })
    @GetMapping("/count/pedestrian")
    public ApiResponseDto<Long> countPedestrianAccidents(
            @Parameter(
                    description = "Two‑digit numeric state code (01–16). Example: 11 = Berlin, 09 = Bavaria",
                    example = "11"
            )
            @RequestParam String stateCode,

            @Parameter(
                    description = "Dataset year (2016–2024)",
                    example = "2023"
            )
            @RequestParam int year
    ) {
        long count = accidentService.countPedestrianAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Pedestrian accident count for state " + stateCode + " in " + year + " retrieved successfully.",
                count,
                meta
        );
    }

    @Operation(
            summary = "Count personal injury accidents for a given state and year",
            description = """
            Returns the number of accidents involving personal injury (Personenschaden)
            in the specified German federal state during the selected year.
            
            Personal injury accidents include:
            • Slight injuries
            • Serious injuries
            • Fatalities
            
            This metric is essential for safety analysis and severity‑based reporting.
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personal injury accident count retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code or year"),
            @ApiResponse(responseCode = "404", description = "No accident data found for the given parameters")
    })
    @GetMapping("/count/personal-injury")
    public ApiResponseDto<Long> countPersonalInjury(
            @Parameter(
                    description = "Two‑digit numeric state code (01–16). Example: 14 = Saxony, 05 = NRW",
                    example = "14"
            )
            @RequestParam String stateCode,

            @Parameter(
                    description = "Dataset year (2016–2024)",
                    example = "2023"
            )
            @RequestParam int year
    ) {
        long count = accidentService.countPersonalInjuryAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Personal injury accident count for state " + stateCode + " in " + year + " retrieved successfully.",
                count,
                meta
        );
    }

    @GetMapping("/analytics/trends/state/{stateCode}")
    @Operation(
            summary = "Get accident trend for a specific state (2016–2024)",
            description = """
            Returns year‑over‑year accident trends for the selected German federal state.
            
            • Uses accident data from 2016 to 2024
            • Computes yearly difference, percentage change, and trend direction
            • Useful for visualizing long‑term accident development
            
            Trend direction:
            • increase → accidents rose compared to previous year
            • decrease → accidents fell
            • no change → stable
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trend data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state code"),
            @ApiResponse(responseCode = "404", description = "No accident data found for the state")
    })
    public ApiResponseDto<?> getTrendsForState(
            @Parameter(
                    description = "Two‑digit German state code (01–16)",
                    example = "14"
            )
            @PathVariable String stateCode
    ) {
        List<AccidentTrendDto> trends = accidentService.getTrendsForState(stateCode);

        // Metadata: latest accident dataset year
        int latestYear = 2024;
        var meta = metadataService.getMetadata(datasetForYear(latestYear));

        return new ApiResponseDto<>(
                "Accident trend for state " + stateCode + " from 2016 to 2024 retrieved successfully.",
                trends,
                meta
        );
    }


    // ------------------------------------------------------------
    // TABLE FILTERING
    // ------------------------------------------------------------

    @Operation(
            summary = "Filter accidents by state, year, and type (all optional, paginated)",
            description = """
            Filters accident records using any combination of:
            • stateCode (optional)
            • year (optional)
            • type (optional)

            If a parameter is missing or invalid, it is ignored.
            At least one filter must be provided.
            """
    )
    @GetMapping("/filter")
    public ApiResponseDto<?> filter(
            @RequestParam(required = false) String stateCode,
            @Parameter(
                    description = """
        Optional accident year. Must exist in the Unfallatlas dataset.
        Example: 2024
        """,
                    example = "2024"
            )
            @RequestParam(required = false) Integer year,
            @Parameter(
                    name = "type",
                    description = """
        Accident Type (UTYP1) based on the official German Unfallatlas dataset.

        UTYP1 describes the *type of accident* and classifies the general traffic situation
        in which the accident occurred.

        Valid values:
        1 = Driving accident
            (Accident caused while driving, e.g., loss of control)
        2 = Accident caused by turning off the road
        3 = Accident caused by turning into a road or by crossing it
        4 = Accident caused by crossing the road
        5 = Accident involving stationary traffic
        6 = Accident between vehicles moving along in the carriageway
        7 = Other accident type

        These definitions are taken from the official Unfallatlas documentation.
        """,
                    example = "3"
            )

            @RequestParam(required = false) Integer type,
            @Parameter(
                    description = """
        Page number for paginated results.
        Starts at 0.
        Default: 0
        """,
                    example = "0"
            )

            @RequestParam(defaultValue = "0") int page,
            @Parameter(
                    description = """
        Number of records per page.
        Recommended range: 200–500 for best performance.
        Default: 500
        """,
                    example = "500"
            )

            @RequestParam(defaultValue = "500") int size
    ) {
        var data = accidentService.filterAccidents(stateCode, year, type, page, size);

        var meta = (year != null)
                ? metadataService.getMetadata(datasetForYear(year))
                : null;

        return new ApiResponseDto<>(
                "Filtered accidents retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // MUNICIPALITY ANALYTICS
    // ------------------------------------------------------------



    @Operation(
            summary = "Get accident counts grouped by municipality for a specific year",
            description = """
            Returns municipality‑level accident counts for the selected state and year.
            Useful for dashboards, regional comparisons, and trend analysis.
            """
    )
    @GetMapping("/municipalities/year")
    public ApiResponseDto<?> getMunicipalitiesByYear(
            @Parameter(description = "Two‑digit numeric state code (01–16)", example = "14")
            @RequestParam String stateCode,

            @Parameter(description = "Dataset year (2016–2024)", example = "2023")
            @RequestParam int year
    ) {
        var data = accidentService.getAccidentsByMunicipalityInStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Municipality accident counts for " + year + " retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // DASHBOARD SUMMARY
    // ------------------------------------------------------------

    @Operation(
            summary = "Retrieve accident summary KPIs for a given year",
            description = """
            Returns high‑level KPIs (Key Performance Indicators) for the selected year,
            such as:
            • Total accidents
            • Fatal accidents
            • Personal injury accidents
            • Property‑damage‑only accidents
            • Accident type distribution

            Designed for dashboard overview panels.
            """
    )
    @GetMapping("/summary")
    public ApiResponseDto<?> getSummary(@Parameter(description = "Dataset year (2016–2024)", example = "2023")
                                            @RequestParam int year) {
        var data = accidentService.getAccidentSummary(year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Accident summary for year " + year + " retrieved successfully.",
                data,
                meta
        );
    }

    // ------------------------------------------------------------
    // ADVANCED ANALYTICS
    // ------------------------------------------------------------

    @Operation(
            summary = "Get top N districts with the highest number of fatal accidents",
            description = """
            Returns a ranked list of districts (Landkreise) with the most fatal accidents
            in the selected year. Useful for hotspot detection and risk assessment.
            """
    )
    @GetMapping("/top-fatal")
    public ApiResponseDto<?> getTopFatal(
            @Parameter(description = "Dataset year (2016–2024)", example = "2022")
            @RequestParam int year,

            @Parameter(description = "Number of top districts to return", example = "5")
            @RequestParam(defaultValue = "5") int limit
    ) {
        var data = accidentService.getTopFatalAccidentsByYear(year, limit);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponseDto<>(
                "Top " + limit + " districts with fatal accidents retrieved successfully.",
                data,
                meta
        );
    }

}
