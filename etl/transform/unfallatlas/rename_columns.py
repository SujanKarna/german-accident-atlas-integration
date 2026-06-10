import pandas as pd

GERMAN_TO_ENGLISH = {
    "ULAND": "state_code",
    "UREGBEZ": "admin_region_code",
    "UKREIS": "district_code",
    "UGEMEINDE": "municipality_code",

    "UJAHR": "year",
    "UMONAT": "month",
    "USTUNDE": "hour",
    "UWOCHENTAG": "weekday",

    # Corrected according to PDF
    "UKATEGORIE": "accident_category",
    "UART": "accident_kind",
    "UTYP1": "accident_type",

    "ULICHTVERH": "light_condition",
    "LICHT": "light_condition",

    # Road condition (unified)
    "USTRZUSTAND": "road_condition",
    "STRZUSTAND": "road_condition",
    "ISTSTRASSENZUSTAND": "road_condition",
    "istStrasse": "road_condition",
    "ISTSTRASSE": "road_condition",

    "LINREFX": "utm_x",
    "LINREFY": "utm_y",

    "XGCSWGS84": "lon",
    "YGCSWGS84": "lat",

    "ISTPKW": "is_car",
    "ISTKRAD": "is_motorcycle",
    "ISTRAD": "is_bicycle",
    "ISTFUSS": "is_pedestrian",
    "ISTGKFZ": "is_goods_vehicle",
    "ISTSONSTIG": "is_other",
    "ISTSONSTIGE": "is_other",
    "ISTSONSTIG_": "is_other",
    "PLST": "plausibility_level",
}




def rename_columns(df: pd.DataFrame) -> pd.DataFrame:
    """
    Rename German Unfallatlas columns to English.
    """
    return df.rename(columns=GERMAN_TO_ENGLISH)
