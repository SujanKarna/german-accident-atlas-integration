import pandas as pd

UNIFIED_SCHEMA = [
    "state_code",
    "admin_region_code",
    "district_code",
    "municipality_code",
    "year",
    "month",
    "hour",
    "weekday",
    "accident_severity",
    "accident_type",
    "accident_category",
    "light_condition",
    "road_surface_condition",
    "is_car",
    "is_motorcycle",
    "is_bicycle",
    "is_pedestrian",
    "is_goods_vehicle",
    "is_other",
    "plausibility_level",
    "utm_x",
    "utm_y",
    "lon",
    "lat",
]

def enforce_unified_schema(df: pd.DataFrame) -> pd.DataFrame:
    for col in UNIFIED_SCHEMA:
        if col not in df.columns:
            df[col] = None
    return df


PARTICIPANT_FLAGS = [
    "is_car",
    "is_motorcycle",
    "is_bicycle",
    "is_pedestrian",
    "is_goods_vehicle",
    "is_other",
]


def normalize_participant_flags(df: pd.DataFrame) -> pd.DataFrame:
    for col in PARTICIPANT_FLAGS:
        df[col] = df[col].apply(lambda x: int(x) if x in [0, 1] else None)
    return df


def transform_step_3(df: pd.DataFrame, year: int) -> pd.DataFrame:
    df = enforce_unified_schema(df)
    df = normalize_participant_flags(df)
    return df
