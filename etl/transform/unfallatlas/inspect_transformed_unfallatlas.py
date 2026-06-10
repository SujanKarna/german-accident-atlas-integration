"""
Inspect the FINAL unified Unfallatlas dataset after applying all transform steps.
This verifies:
- All years share the same schema
- Data types are correct
- Categories are validated
- Combined dataset is ready for loading into PostgreSQL
"""

import pandas as pd
from etl.parse.unfallatlas.parse_unfallatlas import parse_all_years
from etl.transform.unfallatlas.transform_unfallatlas import transform_unfallatlas
pd.set_option("display.max_columns", None)
pd.set_option("display.width", 2000)
pd.set_option("display.max_colwidth", None)


def main():
    print("Loading and transforming Unfallatlas data...")

    dfs = parse_all_years(start=2016, end=2024)

    transformed = []
    for year, df in dfs.items():
        print(f"Transforming year {year}...")
        tdf = transform_unfallatlas(df, year)
        tdf["year"] = year  # add year column for unified dataset
        transformed.append(tdf)

    # Combine all years
    unified = pd.concat(transformed, ignore_index=True)

    print("\n=== UNIFIED DATASET SUMMARY ===")
    print(f"Total rows: {len(unified):,}")
    print(f"Total columns: {len(unified.columns)}")

    print("\n=== COLUMNS ===")
    for col in unified.columns:
        print("  -", col)

    print("\n=== SAMPLE ROWS ===")
    print(unified.head(5))

    print("\n=== DATA TYPES ===")
    print(unified.dtypes)

    print("\n=== Null Values ===")
    print(unified.isnull().sum())

    print("\n=== Duplicate Values ===")
    print(unified.duplicated().sum())

    return unified

if __name__ == "__main__":
    main()
