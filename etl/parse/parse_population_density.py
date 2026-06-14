from etl.parse.common.load_population_raw import load_raw_population_df


def parse_population_density(year: int):
    """
    Parse the raw population density CSV into a clean DataFrame.
    At this stage we only load the raw file.
    Transformation happens later.
    """

    df = load_raw_population_df()
    # Add year column (important for multi-year datasets)
    df["year"] = str(year)

    return df
