from pathlib import Path
import hashlib
import datetime

DATA_DIR = Path("data/population")
DATA_DIR.mkdir(parents=True, exist_ok=True)


def sha256sum(path: Path) -> str:
    """Compute SHA256 checksum for provenance."""
    h = hashlib.sha256()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            h.update(chunk)
    return h.hexdigest()


def extract_population_density(year: int):
    """
    Unfall-style extractor for population density.
    - Automatically finds the CSV file in data/population/
    - Computes SHA256
    - Writes provenance file (including licence)
    """

    # Auto-detect CSV
    csv_files = list(DATA_DIR.glob("*.csv"))

    if not csv_files:
        raise FileNotFoundError("No CSV file found in data/population/")
    if len(csv_files) > 1:
        raise RuntimeError("Multiple CSV files found. Keep only one CSV in data/population/")

    csv_path = csv_files[0]

    print(f"Extracting population density for {year} from {csv_path.name}")

    # Compute checksum
    checksum = sha256sum(csv_path)
    print(f"SHA256: {checksum}")

    # Provenance file (same style as Unfall)
    provenance_file = DATA_DIR / f"{year}_population_density_provenance.txt"

    with open(provenance_file, "w") as f:
        f.write(f"source_url=https://regionalatlas.statistikportal.de\n")
        f.write(f"downloaded_at={datetime.datetime.now(datetime.timezone.utc).isoformat()}Z\n")
        f.write(f"sha256={checksum}\n")
        f.write("licence=Datenlizenz Deutschland - Namensnennung - Version 2.0\n")
        f.write("licence_url=https://www.govdata.de/dl-de/by-2-0\n")

    print(f"Provenance written to {provenance_file}")

    return csv_path

def main():
    extract_population_density(2024)

if __name__ == "__main__":
    main()