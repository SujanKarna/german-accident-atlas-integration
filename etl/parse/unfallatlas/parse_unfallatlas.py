import pandas as pd
from etl.parse.common.load_unfallatlas_raw import load_raw_unfall_df


def normalize_columns(df: pd.DataFrame) -> pd.DataFrame:
    """
    Normalize column names: strip spaces, uppercase.
    """
    df = df.rename(columns={c: c.strip().upper() for c in df.columns})
    return df


def parse_unfallatlas(year: int) -> pd.DataFrame:
    """
    Parse step: load raw Unfallatlas CSV and normalize column names.
    No harmonization or cleaning happens here.
    """
    df = load_raw_unfall_df(year)
    df = normalize_columns(df)
    return df


def parse_all_years(start: int = 2016, end: int = 2024) -> dict[int, pd.DataFrame]:
    """
    Parse all Unfallatlas datasets from start to end year.
    Returns a dict: {year: DataFrame}
    """
    dfs = {}
    for year in range(start, end + 1):
        dfs[year] = parse_unfallatlas(year)
    return dfs
