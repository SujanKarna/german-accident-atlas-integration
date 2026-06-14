from etl.transform.inspect_raw_car_density import inspect_raw_car_density
from etl.load.carDensity.load_car_density import load_car_density
from etl.load.carDensity.load_car_density_metadata import load_car_density_metadata


def main():
    print("\n====================================")
    print("       🚦 Running Car Density ETL        ")
    print("====================================\n")

    print("📥 Inspecting transformed Car Density data...")
    transformed_df = inspect_raw_car_density(2025)
    print("✅ Data inspection complete.\n")

    print("🏛️ Loading car density data...")
    load_car_density(transformed_df)
    print("✅ Car density data loaded.\n")
    
    print("📄 Loading car density metadata...")
    load_car_density_metadata()
    print("✅ Car density metadata loaded.\n")
    
    print("====================================")
    print("       🚦 Car Density ETL Completed       ")
    print("====================================\n")

if __name__ == "__main__":
    main()
