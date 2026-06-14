from pathlib import Path


from etl.load.unfall.load_states import load_states
from etl.load.unfall.load_accident_category import load_accident_category
from etl.load.unfall.load_accident_kind import load_accident_kind
from etl.load.unfall.load_accident_type import load_accident_type
from etl.load.unfall.load_light_condition import load_light_condition
from etl.load.unfall.load_plausibility_level import load_plausibility_level
from etl.load.unfall.load_road_condition import load_road_condition
from etl.load.unfall.load_accident import load_accident
from etl.transform.unfallatlas.inspect_transformed_unfallatlas import main as inspect_transformed_unfallatlas
from etl.load.unfall.load_source_metadata import load_source_metadata


BASE = Path("etl/transform/output/unfall")

def main():
    print("\n====================================")
    print("       🚦 Running Unfall ETL        ")
    print("====================================\n")

    print("📥 Inspecting transformed Unfallatlas data...")
    df = inspect_transformed_unfallatlas()
    print("✅ Data inspection complete.\n")

    print("🏛️ Loading states...")
    load_states(df)
    print("✅ States loaded.\n")

    print("🎨 Loading accident categories...")
    load_accident_category(df)
    print("✅ Accident categories loaded.\n")

    print("🚗 Loading accident kinds...")
    load_accident_kind(df)
    print("✅ Accident kinds loaded.\n")

    print("🚙 Loading accident types...")
    load_accident_type(df)
    print("✅ Accident types loaded.\n")

    print("☀️ Loading light conditions...")
    load_light_condition(df)
    print("✅ Light conditions loaded.\n")

    print("🔍 Loading plausibility levels...")
    load_plausibility_level(df)
    print("✅ Plausibility levels loaded.\n")

    print("🛣️ Loading road conditions...")
    load_road_condition(df)
    print("✅ Road conditions loaded.\n")

    print("🚗 Loading accidents...")
    load_accident(df)
    print("✅ Accidents loaded.\n")

    print("📄 Loading source metadata...")
    load_source_metadata()
    print("✅ Source metadata loaded.\n")
    print("====================================")
    print("       🚦 Unfall ETL Completed       ")
    print("====================================\n")

    
if __name__ == "__main__":
    main()
