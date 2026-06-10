from pathlib import Path
import pandas as pd

from etl.load.unfall.load_states import load_states
from etl.load.unfall.load_districts import load_districts
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
    df = inspect_transformed_unfallatlas()

    load_states(df)
    #load_districts(df)
    load_accident_category(df)
    load_accident_kind(df)
    load_accident_type(df)
    load_light_condition(df)
    load_plausibility_level(df)
    load_road_condition(df)
    load_accident(df)
    load_source_metadata()

if __name__ == "__main__":
    main()
