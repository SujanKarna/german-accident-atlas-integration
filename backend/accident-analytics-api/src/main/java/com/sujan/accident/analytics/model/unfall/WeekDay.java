package com.sujan.accident.analytics.model.unfall;

import lombok.Getter;

@Getter
public enum WeekDay {

    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);



    private final int code;

    WeekDay(int code) {
        this.code = code;
    }

    public static WeekDay fromCode(int code) {
        for (WeekDay w : values()) {
            if (w.code == code) {
                return w;
            }
        }
        throw new IllegalArgumentException("invalid weekdays code " + code);
    }
}
