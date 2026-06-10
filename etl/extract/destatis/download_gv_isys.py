import requests
from pathlib import Path
import zipfile
import hashlib
from datetime import date, timedelta

# Correct base URL (from your active tab)
BASE_URL = (
    "https://www.destatis.de/DE/Themen/Laender-Regionen/Regionales/Gemeindeverzeichnis/Administrativ/Archiv/GV100ADQ"
)

DATA_DIR = Path("data/gv100ad")
DATA_DIR.mkdir(parents=True, exist_ok=True)


def download(url, dest):
    """Stream download to avoid loading large files into memory."""
    resp = requests.get(url, stream=True, timeout=60)
    resp.raise_for_status()
    with open(dest, "wb") as f:
        for chunk in resp.iter_content(chunk_size=8192):
            f.write(chunk)


def is_valid_zip(path: Path) -> bool:
    """Check if a file is a valid ZIP archive."""
    try:
        with zipfile.ZipFile(path, "r") as z:
            return z.testzip() is None
    except:
        return False


def sha256sum(path: Path) -> str:
    """Compute SHA256 checksum for provenance."""
    h = hashlib.sha256()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            h.update(chunk)
    return h.hexdigest()


def get_latest_available_gv100ad():
    """
    GV100AD is ALWAYS published for the *previous* month.
    """
    first_of_month = date.today().replace(day=1)
    last_month_date = first_of_month - timedelta(days=1)

    zip_name = f"GV100AD{last_month_date.day:02d}{last_month_date.month:02d}.zip"

    return (
        last_month_date.year,
        last_month_date.month,
        last_month_date.day,
        zip_name
    )


def download_gv100ad():
    """Download the correct GV100AD ZIP for the previous month."""
    year, month, day, zip_name = get_latest_available_gv100ad()

    # IMPORTANT: include ?__blob=publicationFile
    url = f"{BASE_URL}/{zip_name}?__blob=publicationFile"
    dest_zip = DATA_DIR / zip_name

    print(f"Downloading GV100AD for {day:02d}.{month:02d}.{year} → {zip_name}")
    print(f"URL: {url}")

    download(url, dest_zip)

    if not is_valid_zip(dest_zip):
        dest_zip.unlink(missing_ok=True)
        raise RuntimeError(f"Downloaded file is not a valid ZIP: {zip_name}")

    print(f"Successfully downloaded: {zip_name}")
    return dest_zip


def extract_gv100ad(zip_path: Path):
    extract_dir = DATA_DIR / zip_path.stem
    extract_dir.mkdir(exist_ok=True)

    with zipfile.ZipFile(zip_path, "r") as z:
        z.extractall(extract_dir)

    print(f"Extracted to: {extract_dir}")
    return extract_dir


def write_provenance(zip_path: Path, url: str):
    prov_path = zip_path.with_suffix(".provenance.txt")
    with open(prov_path, "w") as f:
        f.write(f"source_url={url}\n")
        f.write(f"downloaded_at={date.today().isoformat()}\n")
        f.write(f"sha256={sha256sum(zip_path)}\n")
        f.write("licence=Datenlizenz Deutschland - Namensnennung - Version 2.0\n")
        f.write("licence_url=https://www.govdata.de/dl-de/by-2-0\n")
    print(f"Provenance written: {prov_path}")


def main():
    zip_path = download_gv100ad()
    extract_dir = extract_gv100ad(zip_path)

    url = f"{BASE_URL}/{zip_path.name}?__blob=publicationFile"
    write_provenance(zip_path, url)

    print("\nGV100AD download + extract complete.")
    print(f"Data folder: {extract_dir}")


if __name__ == "__main__":
    main()
