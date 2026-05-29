from pathlib import Path
import pandas as pd

DATA_DIR = Path("data/regionalatlas_population")


def list_population_files() -> list[Path]:
    """
    Return all CSV files inside data/regionalatlas_population.
    """
    return sorted([f for f in DATA_DIR.iterdir() if f.suffix.lower() == ".csv"])


def load_raw_population_df(path: Path) -> pd.DataFrame:
    """
    Load a population CSV file with minimal assumptions.
    Skip the first 2 metadata lines.
    """
    df = pd.read_csv(
        path,
        sep=";",
        dtype=str,
        encoding="utf-8",
        skiprows=2,       # skip first 2 metadata lines
        low_memory=False,
    )

    df.columns = ["ags", "region_name", "value"]
    return df
