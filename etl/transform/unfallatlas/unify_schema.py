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

    "accident_category",
    "accident_kind",
    "accident_type",
    
    "light_condition",

    "is_bicycle",
    "is_car",
    "is_pedestrian",
    "is_motorcycle",
    "is_goods_vehicle",
    "is_other",

    "road_condition",

    "utm_x",
    "utm_y",
    "lon",
    "lat",
    
    "plausibility_level",
    
]

def enforce_unified_schema(df: pd.DataFrame) -> pd.DataFrame:
    for col in UNIFIED_SCHEMA:
        if col not in df.columns:
            df[col] = None
    df["state_code"] = df["state_code"].astype(str).str.zfill(2)
    return df


def unify_schema(df: pd.DataFrame, year: int) -> pd.DataFrame:
    df = enforce_unified_schema(df)
    return df

