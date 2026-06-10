from etl.db.connection import get_connection
import pandas as pd


def load_districts(df):
    conn = get_connection()
    cur = conn.cursor()

    # district_code, admin_region_code, municipality_code, state_code
    grouped = df[["municipality_code","district_code", "admin_region_code", "state_code"]].drop_duplicates()

    for _, row in grouped.iterrows():
        municipality_code = str(row["municipality_code"]).zfill(8)
        district_code = str(row["district_code"]).zfill(5)
        admin_region_code = None if pd.isna(row["admin_region_code"]) else str(row["admin_region_code"]).zfill(1)
        state_code = str(row["state_code"]).zfill(2)
        cur.execute("""
            INSERT INTO dim_location (
                municipality_code,
                district_code,
                admin_region_code,
                state_code
            )
            VALUES (%s, %s, %s, %s)
            ON CONFLICT (municipality_code) DO NOTHING
        """, (
            municipality_code,
            district_code,
            admin_region_code,
            state_code
        ))

    conn.commit()
    cur.close()
    conn.close()
