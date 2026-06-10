from etl.db.connection import get_connection

# Fill from PDF
KIND_LABELS = {
    0: "Accident of another kind",
    1: "Collision with another vehicle which starts, stops or is stationary",
    2: "Collision with another vehicle moving ahead or waiting",
    3: "Collision with another vehicle moving laterally in the same direction",
    4: "Collision with another oncoming vehicle",
    5: "Collision with another vehicle which turns into or crosses a road",
    6: "Collision between vehicle and pedestrian",
    7: "Collision with an obstacle in the carriageway",
    8: "Leaving the carriageway to the right",
    9: "Leaving the carriageway to the left"
}

def load_accident_kind(df):
    conn = get_connection()
    cur = conn.cursor()

    for code in sorted(df["accident_kind"].unique()):
        code_int = int(code)
        cur.execute("""
            INSERT INTO dim_accident_kind (kind_code, label)
            VALUES (%s, %s)
            ON CONFLICT (kind_code) DO NOTHING
        """, (code_int, KIND_LABELS[code_int]))

    conn.commit()
    cur.close()
    conn.close()
