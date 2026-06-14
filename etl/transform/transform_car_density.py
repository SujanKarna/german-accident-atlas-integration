import pandas as pd

def transform_car_density(df: pd.DataFrame, year: int) -> pd.DataFrame:
    df.columns = ["state_code", "state_name", "car_density"]

    df = df[df["state_code"].notna()]
    df = df[df["state_code"] != "schluessel"]

    df["state_code"] = df["state_code"].str.zfill(2)
    df["car_density"] = df["car_density"].astype(float)

    df["year"] = str(year)

    return df.reset_index(drop=True)
