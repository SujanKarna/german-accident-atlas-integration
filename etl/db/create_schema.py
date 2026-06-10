from pathlib import Path
from .connection import get_connection

def run_schema():
    sql_path = Path(__file__).parent / "schema.sql"

    conn = get_connection()
    cur = conn.cursor()

    with open(sql_path, "r", encoding="utf-8") as f:
        cur.execute(f.read())

    conn.commit()
    cur.close()
    conn.close()

if __name__ == "__main__":
    run_schema()
