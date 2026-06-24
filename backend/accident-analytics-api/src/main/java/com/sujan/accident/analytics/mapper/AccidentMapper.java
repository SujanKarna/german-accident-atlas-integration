package com.sujan.accident.analytics.mapper;

import com.sujan.accident.analytics.dto.AccidentDto;
import com.sujan.accident.analytics.model.unfall.Accident;
import com.sujan.accident.analytics.service.unfall.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccidentMapper {
    private final StateService stateService;
    private final AccidentCategoryService accidentCategoryService;
    private final AccidentKindService accidentKindService;
    private final AccidentTypeService accidentTypeService;
    private final LightConditionService lightConditionService;
    private final RoadConditionService roadConditionService;
    private final PlausibilityLevelService plausibilityLevelService;

    public AccidentDto toDto(Accident a) {
        return new AccidentDto(
                a.getId(),
                a.getYear(),
                a.getMonth(),
                a.getHour(),

                a.getStateCode(),
                stateService.getStateName(a.getStateCode()),

                a.getMunicipalityCode(),

                a.getAccidentCategoryCode(),
                accidentCategoryService.getLabel(a.getAccidentCategoryCode()),

                a.getAccidentKindCode(),
                accidentKindService.getLabel(a.getAccidentKindCode()),

                a.getAccidentTypeCode(),
                accidentTypeService.getLabel(a.getAccidentTypeCode()),

                a.getLightConditionCode(),
                lightConditionService.getLabel(a.getLightConditionCode()),

                a.getRoadConditionCode(),
                roadConditionService.getLabel(a.getRoadConditionCode()),

                a.getPlausibilityCode(),
                plausibilityLevelService.getLabel(a.getPlausibilityCode()),

                a.getWeekDay() != null ? a.getWeekDay().name() : null,

                a.getLatitude(),
                a.getLongitude(),
                a.getUtmX(),
                a.getUtmY(),

                a.getIsCar(),
                a.getIsMotorcycle(),
                a.getIsBicycle(),
                a.getIsPedestrian(),
                a.getIsGoodsVehicle(),
                a.getIsOthers()
        );
        }


}
