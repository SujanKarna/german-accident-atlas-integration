from etl.parse.common.load_population_raw import load_raw_population_df
from etl.transform.transform_population_density import transform_population_density
from etl.parse.parse_population_density import parse_population_density

def inspect_raw_population(year: int):
    """
    Inspect raw and transformed population density data.
    """

    print(f"\n=== Inspecting Raw Population Density Data for {year} ===")

    # Load raw
    raw_df = load_raw_population_df()
    raw_df = parse_population_density(year)

    print("\n--- RAW: Shape ---")
    print(raw_df.shape)

    print("\n--- RAW: Columns ---")
    print(raw_df.columns.tolist())

    print("\n--- RAW: Head (first 10 rows) ---")
    print(raw_df.head(10))

    # Transform
    transformed_df = transform_population_density(raw_df.copy(), year)

    print("\n=== After Transformation ===")

    print("\n--- TRANSFORMED: Shape ---")
    print(transformed_df.shape)

    print("\n--- TRANSFORMED: Columns ---")
    print(transformed_df.columns.tolist())

    print("\n--- TRANSFORMED: Head (first 10 rows) ---")
    print(transformed_df.head(10))

    print("\n=== Inspection Complete ===\n")

    return transformed_df


if __name__ == "__main__":
    inspect_raw_population(2024)
