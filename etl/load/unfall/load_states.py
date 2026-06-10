
from etl.db.connection import get_connection


STATES = {
    "01": "Schleswig-Holstein",
    "02": "Hamburg",
    "03": "Niedersachsen",
    "04": "Bremen",
    "05": "Nordrhein-Westfalen",
    "06": "Hessen",
    "07": "Rheinland-Pfalz",
    "08": "Baden-Württemberg",
    "09": "Bayern",
    "10": "Saarland",
    "11": "Berlin",
    "12": "Brandenburg",
    "13": "Mecklenburg-Vorpommern",
    "14": "Sachsen",
    "15": "Sachsen-Anhalt",
    "16": "Thüringen"
}

def load_states(df):
    conn = get_connection()
    cur = conn.cursor()


    for code, name in STATES.items():
        cur.execute("""
            INSERT INTO dim_state (state_code, state_name)
            VALUES (%s, %s)
            ON CONFLICT (state_code) DO NOTHING
        """, (code, name))
    conn.commit()
    cur.close()
    conn.close()
