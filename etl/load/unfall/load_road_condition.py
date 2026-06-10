from etl.db.connection import get_connection

# Fill from PDF
ROAD_LABELS = {
    0: "dry",
    1: "wet/damp/slippery",
    2: "slippery(winter)",
}

def load_road_condition(df):
    conn = get_connection()
    cur = conn.cursor()

    for code in sorted(df["road_condition"].unique()):
        code_int = int(code)
        cur.execute("""
            INSERT INTO dim_road_condition (road_condition_code, label)
            VALUES (%s, %s)
            ON CONFLICT (road_condition_code) DO NOTHING
        """, (code_int, ROAD_LABELS[code_int]))

    conn.commit()
    cur.close()
    conn.close()
