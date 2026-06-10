from etl.db.connection import get_connection

# Fill from PDF
LIGHT_LABELS = {
    0: "Daylight",
    1: "Twilight",
    2: "Darkness"
}

def load_light_condition(df):
    conn = get_connection()
    cur = conn.cursor()

    for code in sorted(df["light_condition"].unique()):
        code_int = int(code)
        cur.execute("""
            INSERT INTO dim_light_condition (light_condition_code, label)
            VALUES (%s, %s)
            ON CONFLICT (light_condition_code) DO NOTHING
        """, (code_int, LIGHT_LABELS[code_int]))

    conn.commit()
    cur.close()
    conn.close()
