package com.sujan.accident.analytics.mapper;

import com.sujan.accident.analytics.dto.AccidentDto;
import com.sujan.accident.analytics.model.unfall.Accident;

public class AccidentMapper {


    public AccidentDto toDto(Accident a) {
       return new AccidentDto();
    }
}
