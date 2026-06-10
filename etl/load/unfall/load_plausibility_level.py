from etl.db.connection import get_connection

# Fill from PDF
PLAUS_LABELS = {
    1: "Successful location check according to regular proceedings",
    2: "Successful location check according to advanced proceedings for accidents involving bicycles"
    }

def load_plausibility_level(df):
    conn = get_connection()
    cur = conn.cursor()
    codes = [int(c) for c in df["plausibility_level"].dropna().unique()]
    for code in sorted(codes):
        code_int = int(code)
        cur.execute("""
            INSERT INTO dim_plausibility_level (plausibility_code, label)
            VALUES (%s, %s)
            ON CONFLICT (plausibility_code) DO NOTHING
        """, (code_int, PLAUS_LABELS[code_int]))

    conn.commit()
    cur.close()
    conn.close()
