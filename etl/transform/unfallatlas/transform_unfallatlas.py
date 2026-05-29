import pandas as pd

from etl.transform.unfallatlas.drop_source_ids import drop_source_ids
from etl.transform.unfallatlas.rename_columns import rename_columns
from etl.transform.unfallatlas.unify_schema import unify_schema
from etl.transform.unfallatlas.convert_types import convert_types
from etl.transform.unfallatlas.validate_categories import validate_categories


def transform_unfallatlas(df: pd.DataFrame, year: int) -> pd.DataFrame:
    """
    Apply all transformation steps to a single year's Unfallatlas dataframe.
    Produces a clean, typed, validated fact table ready for loading.
    """

    # Step 1 — remove raw source identifiers
    df = drop_source_ids(df)

    # Step 2 — rename raw German columns to unified English names
    df = rename_columns(df)

    # Step 3 — enforce unified schema (add missing columns as None)
    df = unify_schema(df, year)

    # Step 4 — convert all columns to correct data types
    df = convert_types(df)

    # Step 5 — validate categorical codes (for dimension tables)
    df = validate_categories(df)

    return df
