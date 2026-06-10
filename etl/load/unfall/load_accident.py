import pandas as pd
from etl.db.connection import get_connection

WEEKDAY_LABELS = {
    1: "SUNDAY",
    2: "MONDAY",
    3: "TUESDAY",
    4: "WEDNESDAY",
    5: "THURSDAY",
    6: "FRIDAY",
    7: "SATURDAY"
    
}

def load_accident(df):
    conn = get_connection()
    cur = conn.cursor()
    

    for _, row in df.iterrows():
        plausibility = None if pd.isna(row["plausibility_level"]) else int(row["plausibility_level"])
        road_condition = None if pd.isna(row["road_condition"]) else int(row["road_condition"])
        cur.execute("""
            INSERT INTO fact_accident (
                accident_category_code,
                accident_kind_code,
                accident_type_code,
                light_condition_code,
                plausibility_code,
                road_condition_code,
                municipality_code,
                state_code,
                week_day,
                year,
                month,
                hour,
                latitude,
                longitude,
                utm_x,
                utm_y,
                is_car,
                is_motorcycle,
                is_bicycle,
                is_pedestrian,
                is_goods_vehicle,
                is_others
            )
            VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """, (
            int(row["accident_category"]),
            int(row["accident_kind"]),
            int(row["accident_type"]),
            int(row["light_condition"]),
            plausibility,
            road_condition,
            str(row['municipality_code']).zfill(8),
            str(row['state_code']).zfill(2),
            WEEKDAY_LABELS[int(row["weekday"])],
            int(row["year"]),
            int(row["month"]),
            None if pd.isna(row["hour"]) else int(row["hour"]),
            float(row["lat"]),
            float(row["lon"]),
            None if pd.isna(row["utm_x"]) else float(row["utm_x"]),
            None if pd.isna(row["utm_y"]) else float(row["utm_y"]),
            row["is_car"] == 1,
            row["is_motorcycle"] == 1,
            row["is_bicycle"] == 1,
            row["is_pedestrian"] == 1,
            row["is_goods_vehicle"] == 1,
            row["is_other"] == 1
        ))

    conn.commit()
    cur.close()
    conn.close()
