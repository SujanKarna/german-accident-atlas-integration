import pandas as pd

def transform_population_density(df: pd.DataFrame, year: int) -> pd.DataFrame:
    """
    Transform raw population density data:
    - rename columns
    - drop header row if present
    - convert value to float
    - add year column
    """

    # Rename columns
    df.columns = ["state_code", "state_name", "population_density", "year"]

    # Drop rows where state_code is NaN or equals the header string
    df = df[df["state_code"].notna()]
    df = df[df["state_code"] != "schluessel"]

    # Ensure state_code is always 2-digit string
    df["state_code"] = df["state_code"].str.zfill(2)

    # Convert density to float
    df["population_density"] = df["population_density"].astype(float)


    return df.reset_index(drop=True)
