package com.sujan.accident.analytics.controller;


import com.sujan.accident.analytics.dto.ApiResponse;
import com.sujan.accident.analytics.service.common.SourceMetadataService;
import com.sujan.accident.analytics.service.unfall.AccidentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/accidents")
@Tag(
        name = "Accidents",
        description = "Endpoints for analyzing German accident data (Unfallatlas)."
)
public class AccidentController {

    private final AccidentService accidentService;
    private final SourceMetadataService metadataService;


    public AccidentController(AccidentService accidentService, SourceMetadataService metadataService) {
        this.accidentService = accidentService;
        this.metadataService = metadataService;
    }



    private String datasetForYear(int year) {
        return "unfallatlas_" + year;
    }


    @GetMapping("/metadata/{year}")
    public ApiResponse<?> getMetadataForYear(@PathVariable int year) {
        var meta = metadataService.getMetadata("unfallatlas_" + year);
        return new ApiResponse<>(meta, meta);
    }


    @GetMapping("/earliest-year")
    public ApiResponse<Integer> getEarliestYear() {
        int year = accidentService.getEarliestAccidentYear();
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponse<>(year, meta);
    }

    @GetMapping("/count/state")
    public ApiResponse<Long> countByStateAndYear(
            @RequestParam String stateCode,
            @RequestParam int year
    ) {
        long data = accidentService.countAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponse<>(data, meta);
    }

    @GetMapping("/earliest-year/state")
    public ApiResponse<Integer> getEarliestYearForState(
            @RequestParam String stateCode
    ) {
        int year = accidentService.getEarliestYearForState(stateCode);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponse<>(year, meta);
    }

    @GetMapping("/count/pedestrian")
    public ApiResponse<Long> countPedestrianAccidents(
            @RequestParam String stateCode,
            @RequestParam int year
    ) {
        long data = accidentService.countPedestrianAccidentsByStateAndYear(stateCode, year);
        var meta = metadataService.getMetadata(datasetForYear(year));
        return new ApiResponse<>(data, meta);
    }

//    @GetMapping("/count/city")
//    public ApiResponse<Long> countByCityYearAndType(
//            @RequestParam String city,
//            @RequestParam int year,
//            @RequestParam String type
//    ) {
//        long data = accidentService.countAccidentsByCityYearAndType(city, year, type);
//        var meta = metadataService.getMetadata("unfallatlas");
//        return new ApiResponse<>(data, meta);
//    }

    // TEMPORARY RAW ENDPOINT (no DTO, no mapper)
//    @GetMapping("/all")
//    public ApiResponse<List<Accident>> getAllAccidentsRaw() {
//        List<Accident> data = accidentService.getAllAccidentsRaw(); // we add this method below
//        var meta = metadataService.getMetadata("unfallatlas");
//        return new ApiResponse<>(data, meta);
//    }
}
