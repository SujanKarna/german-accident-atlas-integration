# Population density (population per sq km) is a key demographic factor that can influence traffic patterns and accident rates.

from pathlib import Path
import pandas as pd

DATA_DIR = Path("data/population")


def find_population_file() -> Path:
    """
    Return the population CSV file from data/population/.
    There must be exactly one CSV file.
    """

    files = [f for f in DATA_DIR.iterdir() if f.suffix.lower() == ".csv"]

    if not files:
        raise FileNotFoundError("No CSV file found in data/population/")
    if len(files) > 1:
        raise RuntimeError("Multiple CSV files found in data/population/. Keep only one.")

    return files[0]


def load_raw_population_df() -> pd.DataFrame:
    """
    Load the population density CSV with minimal assumptions.
    All columns are loaded as strings to avoid type corruption.
    """

    file_path = find_population_file()

    df = pd.read_csv(
        file_path,
        sep=";",          # Regionalatlas uses semicolon separator
        dtype=str,
        skiprows=2,        # keep everything as string for now
        encoding="utf-8",
        low_memory=False,
    )

    return df
