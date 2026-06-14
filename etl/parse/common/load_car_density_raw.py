from pathlib import Path
import pandas as pd

DATA_DIR = Path("data/carDensity")


def find_car_density_file() -> Path:
    files = [f for f in DATA_DIR.iterdir() if f.suffix.lower() == ".csv"]

    if not files:
        raise FileNotFoundError("No CSV file found in data/carDensity/")
    if len(files) > 1:
        raise RuntimeError("Multiple CSV files found in data/carDensity/. Keep only one.")

    return files[0]


def load_raw_car_density_df() -> pd.DataFrame:
    file_path = find_car_density_file()

    df = pd.read_csv(
        file_path,
        sep=";",
        dtype=str,
        encoding="utf-8",
        skiprows=2,
        low_memory=False,
    )

    return df
