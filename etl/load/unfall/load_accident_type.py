from etl.db.connection import get_connection

# Fill from PDF
TYPE_LABELS = {
    1: "Driving accident",
    2: "Accident caused by turning off the road",
    3: "Accident caused by turning into a road or by crossing it",
    4: "Accident caused by crossing the road",
    5: "Accident involving stationary",
    6: "Accident between vehicles moving along in carriageway",
    7: "Other accident",
}

def load_accident_type(df):
    conn = get_connection()
    cur = conn.cursor()

    for code in sorted(df["accident_type"].unique()):
        code_int = int(code)
        cur.execute("""
            INSERT INTO dim_accident_type (type_code, label)
            VALUES (%s, %s)
            ON CONFLICT (type_code) DO NOTHING
        """, (code_int, TYPE_LABELS[code_int]))

    conn.commit()
    cur.close()
    conn.close()
