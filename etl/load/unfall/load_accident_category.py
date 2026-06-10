from etl.db.connection import get_connection

# Fill from PDF
CATEGORY_LABELS = {
    1: "Fatal accident",
    2: "Serious injury accident",
    3: "Slight injury accident"
}

def load_accident_category(df):
    conn = get_connection()
    cur = conn.cursor()

    for code in sorted(df["accident_category"].unique()):
        code_int = int(code)
        cur.execute("""
            INSERT INTO dim_accident_category (category_code, label)
            VALUES (%s, %s)
            ON CONFLICT (category_code) DO NOTHING
        """, (code_int, CATEGORY_LABELS[code_int]))

    conn.commit()
    cur.close()
    conn.close()
