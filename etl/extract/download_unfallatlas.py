import requests
from pathlib import Path
import zipfile
import hashlib
import datetime

BASE_URL = "https://www.opengeodata.nrw.de/produkte/transport_verkehr/unfallatlas"
DATA_DIR = Path("data/unfallatlas")
# Ensure the data directory exists/Creates the directory if it doesn't exist, including parent directories if necessary.
DATA_DIR.mkdir(parents=True, exist_ok=True)


def download(url, dest):
    # Stream download to handle large files without loading into memory
    response = requests.get(url, stream=True)
    response.raise_for_status()

    with open(dest, "wb") as f:
        for chunk in response.iter_content(chunk_size=8192):
            f.write(chunk)

def sha256sum(path):
    """Compute SHA256 checksum for provenance."""
    h = hashlib.sha256()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            h.update(chunk)
    return h.hexdigest()

def download_year(year):
    """Download and extract Unfallatlas CSV for a given year."""
    zip_name = f"Unfallorte{year}_EPSG25832_CSV.zip"
    url = f"{BASE_URL}/{zip_name}"
    dest_zip = DATA_DIR / zip_name

    print(f"Downloading accident data for {year}...")
    download(url, dest_zip)

    checksum = sha256sum(dest_zip)
    print(f"Downloaded {zip_name} (SHA256: {checksum})")

    # Extract ZIP into a folder named by year
    extract_dir = DATA_DIR / str(year)
    extract_dir.mkdir(exist_ok=True)

    with zipfile.ZipFile(dest_zip, "r") as z:
        z.extractall(extract_dir)

    # Save provenance
    provenance_file = DATA_DIR / f"{year}_provenance.txt"
    with open(provenance_file, "w") as f:
        f.write(f"source_url={url}\n")
        f.write(f"downloaded_at={datetime.datetime.now(datetime.timezone.utc).isoformat()}Z\n")
        f.write(f"sha256={checksum}\n")
        f.write("licence=Datenlizenz Deutschland - Namensnennung - Version 2.0\n")
        f.write("licence_url=https://www.govdata.de/dl-de/by-2-0\n")

def download_metadata_english():
    """Download the English metadata PDF."""
    url = f"{BASE_URL}/DSB_Unfallatlas_EN.pdf"
    dest = DATA_DIR / "DSB_Unfallatlas_EN.pdf"

    print("Downloading English metadata PDF...")
    download(url, dest)

    checksum = sha256sum(dest)
    with open(DATA_DIR / "metadata_provenance.txt", "w") as f:
        f.write(f"source_url={url}\n")
        f.write(f"downloaded_at={datetime.datetime.now(datetime.timezone.utc).isoformat()}Z\n")
        f.write(f"sha256={checksum}\n")
        f.write("licence=Datenlizenz Deutschland - Namensnennung - Version 2.0\n")
        f.write("licence_url=https://www.govdata.de/dl-de/by-2-0\n")

def main():
    download_metadata_english()

    for year in range(2016, 2025):
        download_year(year)

if __name__ == "__main__":
    main()
