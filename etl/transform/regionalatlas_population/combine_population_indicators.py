import pandas as pd
from etl.parse.common.load_population_raw import list_population_files
from etl.parse.regionalatlas_population.parse_population import parse_population_file


def combine_population_indicators() -> pd.DataFrame:
    """
    Parse and combine all population CSVs into one fact table.
    """

    frames = []

    for path in list_population_files():
        df, indicator_id, year = parse_population_file(path)
        df["indicator_id"] = indicator_id
        df["year"] = year
        frames.append(df)

    return pd.concat(frames, ignore_index=True)

