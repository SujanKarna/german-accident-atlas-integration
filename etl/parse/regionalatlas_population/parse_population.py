import re
from pathlib import Path
import pandas as pd
from etl.parse.common.load_population_raw import load_raw_population_df


def extract_indicator_name(path: Path) -> str:
    """
    Reads the first line of the CSV to extract the indicator name.
    Example: ';Bevölkerung 0 bis 17 Jahre (%);'
    """
    with open(path, "r", encoding="utf-8") as f:
        first_line = f.readline().strip()

    return first_line.strip(";")


def indicator_name_to_id(name: str) -> str:
    """
    Convert indicator name into a clean indicator_id.
    """

    # Match "0 bis 17"
    m = re.search(r"(\d+)\s*bis\s*(\d+)", name)
    if m:
        start, end = m.groups()
        return f"pop_{start}_{end}"

    # Match "65+"
    m = re.search(r"(\d+)\s*\+", name)
    if m:
        start = m.group(1)
        return f"pop_{start}_plus"

    
    # Match "65 Jahre und älter" or "65 Jahre und mehr"
    m = re.search(r"(\d+)\s+Jahre\s+und\s+(älter|mehr)", name, re.IGNORECASE)
    if m:
        start = m.group(1)
        return f"pop_{start}_plus"
    

    return "pop_unknown"

def extract_year_from_filename(path: Path) -> int:
    """
    Extract the first 4-digit year from the filename.
    Example: 'K-2024-AI002-2-5--AI0203--2026-05-23.csv' -> 2024
    """
    import re
    m = re.search(r"(\d{4})", path.name)
    if not m:
        raise ValueError(f"No 4-digit year found in filename: {path.name}")
    return int(m.group(1))


def parse_population_file(path: Path) -> tuple[pd.DataFrame, str, int]:
    """
    Parse a population CSV and return (df, indicator_id, year).
    """

    indicator_name = extract_indicator_name(path)
    indicator_id = indicator_name_to_id(indicator_name)
    year = extract_year_from_filename(path)

    df = load_raw_population_df(path)

    df["ags"] = df["ags"].astype(str).str.strip().str.zfill(5)
    df["value"] = df["value"].astype(str).str.replace(",", ".", regex=False).astype(float)

    return df, indicator_id, year



