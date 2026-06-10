"""
Inspect the column structure of Unfallatlas DataFrames for all years.
This helps us understand:
- which columns exist
- differences between years
- ESRI leftover columns
- datatype inconsistencies
"""

"""
Inspect the column structure of Unfallatlas DataFrames for all years.
"""
import pandas as pd
from etl.parse.unfallatlas.parse_unfallatlas import parse_all_years



pd.set_option("display.max_columns", None)
pd.set_option("display.width", 2000)
pd.set_option("display.max_colwidth", None)


def main():
    dfs = parse_all_years(start=2016, end=2024)

    all_columns = {}

    for year, df in dfs.items():
        print(f"\n[{year}] Columns ({len(df.columns)}) & Rows ({len(df)}):")
        
        # ✔ Row + column count + dtypes (all in one)
        print("\n--- DataFrame Info ---")
        df.info()

        # ✔ Duplicate row count
        dup_count = df.duplicated().sum()
        print(f"\nDuplicate rows: {dup_count}")

        # ✔ Show actual duplicated rows (if any)
        if dup_count > 0:
            print("\nDuplicated row values:")
            print(df[df.duplicated()])
        
        # Null counts
        print("\nNull counts:")
        for col in df.columns:
            nulls = df[col].isna().sum()
            if nulls > 0:
                print(f"  {col}: {nulls}")

        all_columns[year] = set(df.columns)

        print(f"\n[{year}] Top 5 rows:")
        print(df.head(5))

    print("\n=== SUMMARY OF COLUMN DIFFERENCES ===")

    # Union of all columns across all years
    union = set().union(*all_columns.values())
    print(f"\nTotal unique columns across all years: {len(union)}")

    for col in sorted(union):
        present_in = [y for y in sorted(all_columns.keys()) if col in all_columns[y]]
        print(f"{col}: present in {present_in}")


if __name__ == "__main__":
    main()
