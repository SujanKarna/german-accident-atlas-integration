from etl.parse.common.load_car_density_raw import load_raw_car_density_df
from etl.transform.transform_car_density import transform_car_density


def inspect_raw_car_density(year: int):
    print(f"\n=== Inspecting Raw Car Density Data for {year} ===")

    raw_df = load_raw_car_density_df()

    print("\n--- RAW: Shape ---")
    print(raw_df.shape)

    print("\n--- RAW: Columns ---")
    print(raw_df.columns.tolist())

    print("\n--- RAW: Head ---")
    print(raw_df.head(10))

    transformed_df = transform_car_density(raw_df.copy(), year)

    print("\n=== After Transformation ===")

    print("\n--- TRANSFORMED: Shape ---")
    print(transformed_df.shape)

    print("\n--- TRANSFORMED: Columns ---")
    print(transformed_df.columns.tolist())

    print("\n--- TRANSFORMED: Head ---")
    print(transformed_df.head(10))

    print("\n=== Inspection Complete ===\n")

    return transformed_df

if __name__ == "__main__":
    inspect_raw_car_density(2025)
