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

from etl.parse.unfallatlas.parse_unfallatlas import parse_all_years


def main():
    dfs = parse_all_years(start=2016, end=2024)

    all_columns = {}

    for year, df in dfs.items():
        print(f"\n[{year}] Columns ({len(df.columns)}):")
        for col in df.columns:
            print("  -", col)

        all_columns[year] = set(df.columns)

    print("\n=== SUMMARY OF COLUMN DIFFERENCES ===")

    # Union of all columns across all years
    union = set().union(*all_columns.values())
    print(f"\nTotal unique columns across all years: {len(union)}")

    for col in sorted(union):
        present_in = [y for y in sorted(all_columns.keys()) if col in all_columns[y]]
        print(f"{col}: present in {present_in}")


if __name__ == "__main__":
    main()
