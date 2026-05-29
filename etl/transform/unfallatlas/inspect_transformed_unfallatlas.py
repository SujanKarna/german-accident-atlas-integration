"""
Inspect the column structure AFTER applying the transform steps.
This verifies:
- ID columns are removed
- German → English renaming is correct
- All years have consistent schemas
"""

from etl.parse.unfallatlas.parse_unfallatlas import parse_all_years
from etl.transform.accidents.drop_source_ids import drop_source_ids
from etl.transform.accidents.rename_columns import rename_columns


def transform_preview(df):
    """Apply only the completed transform steps."""
    df = drop_source_ids(df)      # Step 1
    df = rename_columns(df)       # Step 2
    return df


def main():
    dfs = parse_all_years(start=2016, end=2024)

    transformed = {}
    for year, df in dfs.items():
        tdf = transform_preview(df)
        transformed[year] = tdf

        print(f"\n[{year}] Columns after transform ({len(tdf.columns)}):")
        for col in tdf.columns:
            print("  -", col)

    print("\n=== SUMMARY OF TRANSFORMED COLUMN DIFFERENCES ===")

    # Collect all columns across years
    all_columns = {year: set(df.columns) for year, df in transformed.items()}
    union = set().union(*all_columns.values())

    print(f"\nTotal unique transformed columns: {len(union)}")

    for col in sorted(union):
        present_in = [y for y in sorted(all_columns.keys()) if col in all_columns[y]]
        print(f"{col}: present in {present_in}")


if __name__ == "__main__":
    main()
