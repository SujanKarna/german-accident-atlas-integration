"""
Master ETL runner for:
- dim_state
- dim_population_density
- dim_car_density
- source_metadata
"""

from etl.db.create_schema import run_schema
from etl.load.carDensity.run_load_car_density import main as run_load_car_density
from etl.load.population.run_load_population import main as run_load_population
from etl.load.unfall.run_load_unfall import main as run_load_unfall


def main():
    print("\n====================================")
    print("         STARTING FULL ETL          ")
    print("====================================\n")

    # 1. Create schema
    run_schema()

    # 2. Load accident data
    print("Loading accident data...")
    run_load_unfall()
    print("✅ Accident data loaded.\n")

    # 3. Load population density
    print("Loading population density...")
    run_load_population()
    print("✅ Population density loaded.\n")

    # 4. Load car density
    print("Loading car density...")
    run_load_car_density()
    print("✅ Car density loaded.\n")

    

    print("====================================")
    print("        FULL ETL COMPLETED          ")
    print("====================================\n")

if __name__ == "__main__":
    main()