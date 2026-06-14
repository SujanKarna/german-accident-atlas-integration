from etl.db.connection import get_connection


def load_population_density(df):
    """
    Load population density into dim_population_density.
    Matches the style of load_states().
    """

    conn = get_connection()
    cur = conn.cursor()

    for _, row in df.iterrows():
        cur.execute("""
            INSERT INTO dim_population_density (
                state_code,
                population_density,
                year
            )
            VALUES (%s, %s, %s)
            ON CONFLICT (state_code, year)
            DO UPDATE SET
                population_density = EXCLUDED.population_density
        """, (
            row["state_code"],
            row["population_density"],
            row["year"]
        ))

    conn.commit()
    cur.close()
    conn.close()
