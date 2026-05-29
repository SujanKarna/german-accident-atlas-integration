import pandas as pd

# -----------------------------
# Valid sets for each category
# -----------------------------

VALID_STATES = {
    "01","02","03","04","05","06","07","08",
    "09","10","11","12","13","14","15","16"
}

VALID_SEVERITY = {1, 2, 3}

VALID_ACCIDENT_TYPE = {1, 2, 3, 4, 5, 6, 7}

VALID_ACCIDENT_CATEGORY = {1, 2, 3}

VALID_LIGHT = {0, 1, 2}

VALID_SURFACE = {0, 1, 2}

VALID_WEEKDAY = {1, 2, 3, 4, 5, 6, 7}

VALID_PLAUSIBILITY = {1, 2}   # Only for 2023+


# -----------------------------
# Column → valid set mapping
# -----------------------------

CATEGORY_MAPS = {
    "state_code": VALID_STATES,
    "accident_severity": VALID_SEVERITY,
    "accident_type": VALID_ACCIDENT_TYPE,
    "accident_category": VALID_ACCIDENT_CATEGORY,
    "light_condition": VALID_LIGHT,
    "road_surface_condition": VALID_SURFACE,
    "weekday": VALID_WEEKDAY,
    "plausibility_level": VALID_PLAUSIBILITY,
}


# -----------------------------
# Validation function
# -----------------------------

def validate_categories(df: pd.DataFrame) -> pd.DataFrame:
    """
    Validate all categorical columns against their allowed sets.
    If new or unknown values appear, print warnings so dimension tables can be updated.
    """

    for col, valid_set in CATEGORY_MAPS.items():
        if col not in df.columns:
            continue

        # Drop None/NaN, get unique values
        values = set(df[col].dropna())

        # Find unexpected values
        invalid = values - valid_set

        if invalid:
            print(f"[WARNING] Unknown values in '{col}': {invalid}")

    return df
