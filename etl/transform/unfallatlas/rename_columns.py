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

    "UKATEGORIE": "accident_severity",
    "UART": "accident_type",
    "UTYP1": "accident_category",

    "ULICHTVERH": "light_condition",
    "LICHT": "light_condition",

    "USTRZUSTAND": "road_surface_condition",
    "STRZUSTAND": "road_surface_condition",
    "ISTSTRASSENZUSTAND": "road_surface_condition",
    "istStrasse": "road_surface_condition",
    "ISTSTRASSE": "road_surface_condition",



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
