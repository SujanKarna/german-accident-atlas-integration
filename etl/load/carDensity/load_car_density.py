from etl.db.connection import get_connection

def load_car_density(df):
    conn = get_connection()
    cur = conn.cursor()

    for _, row in df.iterrows():
        cur.execute("""
            INSERT INTO dim_car_density (
                state_code,
                car_density,
                year
            )
            VALUES (%s, %s, %s)
            ON CONFLICT (state_code, year)
            DO UPDATE SET
                car_density = EXCLUDED.car_density
        """, (
            row["state_code"],
            row["car_density"],
            row["year"]
        ))

    conn.commit()
    cur.close()
    conn.close()
