from etl.db.connection import get_connection
from pathlib import Path
from etl.parse.common.parse_provenance import parse_provenance_file


def load_population_metadata():
    DATA_DIR = Path("data/population")
    conn = get_connection()
    cur = conn.cursor()

    # Matches files like: 2024_population_density_provenance.txt
    for file in DATA_DIR.glob("*_population_density_provenance.txt"):
        year = file.stem.split("_")[0]  # "2024_population_density_provenance" → "2024"
        parsed = parse_provenance_file(file)

        dataset = f"population_density_{year}"
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

    print("Population metadata loaded.")
