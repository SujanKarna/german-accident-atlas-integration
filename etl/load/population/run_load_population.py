from etl.load.population.load_population_density import load_population_density
from etl.load.population.load_population_metadata import load_population_metadata
from etl.transform.inspect_raw_population import inspect_raw_population


def main():
    print("\n====================================")
    print("       🚦 Running Population ETL        ")
    print("====================================\n")

    print("📥 Inspecting transformed Population data...")
    df = inspect_raw_population(2024)
    print("✅ Data inspection complete.\n")

    print("🏛️ Loading population density data...")
    load_population_density(df)
    print("✅ Population density data loaded.\n")

    print("📄 Loading population metadata...")
    load_population_metadata()
    print("✅ Population metadata loaded.\n")

    print("====================================")
    print("       🚦 Population ETL Completed       ")
    print("====================================\n")

if __name__ == "__main__":
    main()
