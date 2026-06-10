import pandas as pd

INT_COLUMNS = [
    "year",
    "month",
    "hour",
    "weekday",
    "accident_kind",
    "accident_type",
    "accident_category",
    "light_condition",
    "road_condition",
    "plausibility_level",
]

STRING_COLUMNS = [
    "state_code",
    "admin_region_code",
    "district_code",
    "municipality_code",
]

FLOAT_COLUMNS = [
    "utm_x",
    "utm_y",
    "lon",
    "lat",
]

PARTICIPANT_FLAGS = [
    "is_car",
    "is_motorcycle",
    "is_bicycle",
    "is_pedestrian",
    "is_goods_vehicle",
    "is_other",
]


def convert_types(df: pd.DataFrame) -> pd.DataFrame:
    # Convert integer-like columns
    for col in INT_COLUMNS:
        if col in df.columns:
            df[col] = df[col].apply(lambda x: int(x) if pd.notna(x) else None)

    # Convert string-like columns
    for col in STRING_COLUMNS:
        if col in df.columns:
            df[col] = df[col].astype("string") 

    # Convert float-like columns
    for col in FLOAT_COLUMNS:
        if col in df.columns:
            df[col] = df[col].apply(lambda x: float(str(x).replace(",", ".")) if pd.notna(x) else None
)


    # Participant flags
    for col in PARTICIPANT_FLAGS:
        df[col] = df[col].apply(lambda x: int(x) if x in ["0", "1"] else None)

    return df
