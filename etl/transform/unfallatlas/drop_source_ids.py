import pandas as pd

# All possible ID columns found across years
SOURCE_ID_COLUMNS = [
    "OBJECTID",
    "OBJECTID_1",
    "OID_",
    "FID",
    "ID",
    "ID_1",
    "UIDENTISTLA",
    "UIDENTSTLAE",
    "UIDENTSTLA"
]



def drop_source_ids(df: pd.DataFrame) -> pd.DataFrame:
    """
    Remove all source-specific ID columns from the DataFrame.
    """
    cols_to_drop = [c for c in SOURCE_ID_COLUMNS if c in df.columns]
    return df.drop(columns=cols_to_drop, errors="ignore")
