from pathlib import Path
import pandas as pd

DATA_DIR = Path("data/unfallatlas")


def find_unfall_file(year: int) -> Path:
    """
    Return the Unfallatlas CSV/TXT file for a given year.
    """
    folder = DATA_DIR / str(year)

    if not folder.exists():
        raise FileNotFoundError(f"Folder does not exist for year {year}: {folder}")

    files = [f for f in folder.iterdir() if f.suffix.lower() in [".csv", ".txt"]]

    if not files:
        raise FileNotFoundError(f"No CSV/TXT file found for year {year} in {folder}")

    # For Unfallatlas there should be exactly one relevant file per year
    return files[0]


def load_raw_unfall_df(year: int) -> pd.DataFrame:
    """
    Load the Unfallatlas file for a given year with minimal assumptions.
    All columns are loaded as strings to avoid type corruption.
    """
    file_path = find_unfall_file(year)

    df = pd.read_csv(
        file_path,
        sep=";",          # Unfallatlas uses semicolon separator
        dtype=str,        # keep everything as string for now
        encoding="utf-8",
        low_memory=False,
    )

    return df
