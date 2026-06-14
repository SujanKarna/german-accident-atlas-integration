from etl.db.connection import get_connection
from pathlib import Path
from etl.parse.common.parse_provenance import parse_provenance_file


def load_car_density_metadata():
    DATA_DIR = Path("data/car_density")
    conn = get_connection()
    cur = conn.cursor()

    for file in DATA_DIR.glob("*_car_density_provenance.txt"):
        year = file.stem.split("_")[0]
        parsed = parse_provenance_file(file)

        dataset = f"car_density_{year}"
        source_url = parsed.get("source_url")
        license = parsed.get("licence")
        license_url = parsed.get("licence_url")
        downloaded_at = parsed.get("downloaded_at")
        sha256 = parsed.get("sha256")

        cur.execute("""
            INSERT INTO source_metadata (
                dataset,
                source_url,
                license,
                license_url,
                downloaded_at,
                sha256
            )
            VALUES (%s, %s, %s, %s, %s, %s)
            ON CONFLICT (dataset) DO UPDATE SET
                source_url = EXCLUDED.source_url,
                license = EXCLUDED.license,
                license_url = EXCLUDED.license_url,
                downloaded_at = EXCLUDED.downloaded_at,
                sha256 = EXCLUDED.sha256;
        """, (dataset, source_url, license, license_url, downloaded_at, sha256))

    conn.commit()
    cur.close()
    conn.close()

    print("Car density metadata loaded.")
